package com.baiwen.api.controller;

import com.baiwen.api.message.OnlineUser;
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
@Api(description = "用户仓库接口")
@RequestMapping(value = "userGoods")
@ResponseBody
public class UserGoodsController extends BaseController{

    @Autowired
    private IUserGoodsService userGoodsService;

    @Autowired
    private IUserService userService;

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
            List<Map<String, Object>> mapList = convertList(userGoodsList);
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
}
