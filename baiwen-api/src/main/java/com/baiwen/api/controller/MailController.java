package com.baiwen.api.controller;

import com.baiwen.business.Enum.FlowType;
import com.baiwen.business.Enum.GoodsStatus;
import com.baiwen.business.Enum.UserType;
import com.baiwen.business.model.OperationFlow;
import com.baiwen.business.model.User;
import com.baiwen.business.model.UserGoods;
import com.baiwen.business.service.IOperationFlowService;
import com.baiwen.business.service.IUserGoodsService;
import com.baiwen.business.service.IUserService;
import com.baiwen.common.util.DateUtils;
import com.baiwen.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@Api(description = "商家邮箱接口")
@RequestMapping(value = "mail")
@ResponseBody
public class MailController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserGoodsService userGoodsService;

    @Autowired
    private IOperationFlowService operationFlowService;

    @RequestMapping(value = "getMailList",method = RequestMethod.POST)
    @ApiOperation(value = "获得邮箱商品列表" ,  notes="商家用户传入openId")
    public String getMailList(@RequestBody Map map){
        try {
            String openId = map.get("openId").toString();
            User user = userService.getUserByOpenId(openId);
            if (user == null) {
                return setResult(false, 2000, "用户不存在", null);
            }
            if(user.getUserType() != UserType.BUSINESS.getCode()){
                return setResult(false, 2000, "非商家用户", null);
            }
            Map params = new HashMap();
            params.put("businessId",user.getUserId());
            List<UserGoods> mailList = userGoodsService.getMailList(params);
            List<Map<String, Object>> mapList = convertList(mailList);
            return setResult(true,200,"成功",mapList);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return setResult(false,2001,e.getMessage(),null);
        }
    }

    @RequestMapping(value = "confirmMailGoods",method = RequestMethod.POST)
    @ApiOperation(value = "商家确认使用商品" ,  notes="商家用户传入openId和列表userGoodsId")
    public String confirmMailGoods(@RequestBody Map map){
        try {
            String openId = map.get("openId").toString();
            User user = userService.getUserByOpenId(openId);
            if (user == null) {
                return setResult(false, 2000, "用户不存在", null);
            }
            if (user.getUserType() != UserType.BUSINESS.getCode()) {
                return setResult(false, 2000, "非商家用户", null);
            }
            String userGoodsId = (String) map.get("userGoodsId");
            if(StringUtils.isEmpty(userGoodsId)){
                return setResult(false,2000,"参数为空",null);
            }
            UserGoods userGoods = userGoodsService.getUserGoodsById(Integer.valueOf(userGoodsId));
            if(userGoods == null){
                return setResult(false,2000,"数据不存在",null);
            }
            int businessId = userGoods.getBusinessId();
            int userId = user.getUserId();
            if(businessId != userId){
                return setResult(false,2000,"非商家商品，不能操作",null);
            }
            userGoods.setStatusCode(GoodsStatus.USED.getCode());
            userGoodsService.updateUserGoods(userGoods);
            //记录操作流水
            Date now = new Date();
            String dateStr = DateUtils.convertToString(now, "yyyy-MM-dd HH:mm:ss");
            OperationFlow flow = new OperationFlow();
            flow.setUserId(userGoods.getUserId());
            flow.setGoodsId(userGoods.getGoodsId());
            flow.setUserGoodsId(userGoods.getId());
            flow.setBusinessId(userGoods.getBusinessId());
            flow.setFlowType(FlowType.CONFIRM.getCode());
            String content = "商家："+user.getUserName()+"已于"+ dateStr + "确认用户："+userGoods.getUserName()+"使用商品：" + userGoods.getDescribe();
            flow.setContent(content);
            operationFlowService.addOperationFlow(flow);
            return setResult(true,200,"确认成功",null);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return setResult(false,2001,e.getMessage(),null);
        }
    }
}
