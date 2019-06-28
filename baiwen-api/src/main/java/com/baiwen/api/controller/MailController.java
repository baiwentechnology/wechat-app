package com.baiwen.api.controller;

import com.baiwen.business.Enum.UserType;
import com.baiwen.business.model.User;
import com.baiwen.business.model.UserGoods;
import com.baiwen.business.service.IUserGoodsService;
import com.baiwen.business.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
