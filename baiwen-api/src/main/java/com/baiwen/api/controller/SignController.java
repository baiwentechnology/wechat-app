package com.baiwen.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.baiwen.api.message.OnlineUser;
import com.baiwen.business.model.User;
import com.baiwen.business.service.IUserService;
import com.baiwen.business.service.IsignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@Slf4j
@Api(description = "签到")
@RequestMapping(value = "sign")
@ResponseBody
public class SignController extends BaseController{
    @Autowired
    private IsignService isignService;
    @Autowired
    private IUserService userService;

    @ApiOperation(value = "获得用户所有的签到日期以及是否需要签到" ,  notes="传入openId，返回total为签到总数，sign 为false为需要签到，true为不需要签到")
    @RequestMapping(value = "/getAllSign",method = RequestMethod.POST)
    public String getAllSign(@RequestBody Map map){
        OnlineUser onlineUser = this.getOnlineUser(map.get("openId").toString());
        if(onlineUser.isOnLine()==false){
            return setResult(false, 2001, "请登录", null);
        }
        try {
            JSONObject jsonObject = isignService.getSignData(onlineUser.getUserId());
            return setResult(true, 200, "", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return setResult(false, 2000, "登录出错，请联系管理员", null);
        }
    }
    @ApiOperation(value = "进行签到并获得奖励" ,  notes="传入openId即可，返回200即为成功，返回其他均为失败")
    @RequestMapping(value = "/makeSign",method = RequestMethod.POST)
    public String makeSign(@RequestBody Map map){
        try {
            OnlineUser onlineUser = this.getOnlineUser(map.get("openId").toString());
            if(onlineUser.isOnLine()==false){
                return setResult(false, 2001, "请登录", null);
            }
            JSONObject jsonObject = isignService.makeSign(onlineUser.getUserId());
            String success = jsonObject.get("success").toString();
            if("false".equals(success)){
                return setResult(true, 200, "签到失败", jsonObject.get("message").toString());
            }
            User user = userService.getUserByOpenId(map.get("openId").toString());
            int after = user.getWaterDrop()+200;
            if(after > 300){
                after = 300;
            }
            user.setWaterDrop(after);
            userService.addOrUpdateUser(user);
            return setResult(true, 200, "签到成功", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return setResult(false, 2000, "出错，请联系管理员", null);
        }
    }


}
