package com.baiwen.api.controller;

import com.baiwen.api.message.OnlineUser;
import com.baiwen.business.Enum.FlowType;
import com.baiwen.business.Enum.GoodsStatus;
import com.baiwen.business.model.OperationFlow;
import com.baiwen.business.model.User;
import com.baiwen.business.model.UserGoods;
import com.baiwen.business.service.IOperationFlowService;
import com.baiwen.business.service.IUserGoodsService;
import com.baiwen.business.service.IUserService;
import com.baiwen.common.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@Slf4j
@Api(description = "用户仓库接口")
@RequestMapping(value = "userGoods")
@ResponseBody
public class UserGoodsController extends BaseController{

    @Autowired
    private IUserGoodsService userGoodsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOperationFlowService operationFlowService;

    @RequestMapping(value = "getUserGoodsList",method =  RequestMethod.POST)
    @ApiOperation(value = "获取用户仓库列表" ,  notes="传入openId")
    public String getUserGoodsList(@RequestBody Map params){
        try {
            String openId = params.get("openId").toString();
            User user = userService.getUserByOpenId(openId);
            if (user == null) {
                return setResult(false, 2000, "用户不存在", null);
            }
            int userId = user.getUserId();
            Map param = new HashMap();
            param.put("userId",userId);
            List<UserGoods> userGoodsList = userGoodsService.getUserGoodsList(param);
            if(userGoodsList == null || userGoodsList.size() == 0){
                return setResult(false,200,"暂无数据",null);
            }
            //判断是否过期
            Date now = new Date();
            List<UserGoods> updateList = new ArrayList<>();
            List<UserGoods> resultList = new ArrayList<>();
            for(UserGoods userGoods : userGoodsList){
                Date expirationTime = userGoods.getExpirationTime();
                if(now.getTime() > expirationTime.getTime() && (userGoods.getStatusCode() == GoodsStatus.NOTUSED.getCode())){
                    userGoods.setStatusCode(GoodsStatus.EXPIRE.getCode());
                    userGoods.setUpdateTime(now);
                    updateList.add(userGoods);
                }
                resultList.add(userGoods);
            }
            //更新过期商品
            userGoodsService.expreUserGoodsList(updateList);
            //返回
            List<Map<String, Object>> mapList = convertList(resultList);
            for(Map<String, Object> map : mapList){
                String expirationTime = (String) map.get("expirationTime");
                expirationTime = DateUtils.convertToString(DateUtils.convertToDate(expirationTime, DateUtils.YYYYMMDDHHMMSS_W_C), DateUtils.YYYYMMDD_W);
                map.put("expirationTime",expirationTime);
                String createTime = (String) map.get("createTime");
                createTime = DateUtils.convertToString(DateUtils.convertToDate(createTime, DateUtils.YYYYMMDDHHMMSS_W_C), DateUtils.YYYYMMDD_W);
                map.put("createTime",createTime);
            }
            Map result = new HashMap();
            result.put("gold",user.getGold());
            result.put("waterDrop",user.getWaterDrop());
            result.put("goodsList",mapList);
            return setResult(true,200,"成功",result);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return setResult(false,2000,e.getMessage(),null);
        }
    }

    @RequestMapping(value = "toUserGoods" ,method =  RequestMethod.POST)
    @ApiOperation(value = "用户使用兑换卷" ,  notes="传入openId,userGoodsId")
    public String toUseGoods(@RequestBody Map map){
        try {
            String openId = map.get("openId").toString();
            User user = userService.getUserByOpenId(openId);
            if (user == null) {
                return setResult(false, 2000, "用户不存在", null);
            }
            Integer userGoodsId = (Integer) map.get("userGoodsId");
            UserGoods userGoods = userGoodsService.getUserGoodsById(userGoodsId);
            if(userGoods == null){
                return setResult(false, 2000, "物品不存在", null);
            }
            Date expirationTime = userGoods.getExpirationTime();
            Date now = new Date();
            String dateStr = DateUtils.convertToString(now, "yyyy-MM-dd HH:mm:ss");
            if(now.getTime() > expirationTime.getTime()){
                return setResult(false, 2000, "物品已过期", null);
            }
            userGoods.setStatusCode(GoodsStatus.INUSED.getCode());
            userGoodsService.updateUserGoods(userGoods);
            //记录操作流水
            OperationFlow flow = new OperationFlow();
            flow.setUserId(user.getUserId());
            flow.setGoodsId(userGoods.getGoodsId());
            flow.setUserGoodsId(userGoods.getId());
            flow.setBusinessId(userGoods.getBusinessId());
            flow.setFlowType(FlowType.USEGOODS.getCode());
            String content = "用户："+user.getUserName()+"已于"+ dateStr + "使用商品：" + userGoods.getDescribe();
            flow.setContent(content);
            operationFlowService.addOperationFlow(flow);
            return setResult(true,200,"已发送给商家进行确认",null);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return setResult(false,2000,e.getMessage(),null);
        }
    }
}
