package com.baiwen.api.controller;

import com.baiwen.api.message.OnlineUser;
import com.baiwen.business.model.UserConfig;
import com.baiwen.business.service.IUserConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@Api(description = "用户设置主接口")
@RequestMapping(value = "userConfig")
public class UserConfigController extends BaseController{

    @Autowired
    private IUserConfigService userConfigService;

    @ApiOperation(value = "设置用户配置信息" ,  notes="传入openId，configType，configValue，configType为参数设置类型，音乐开关为musicSwitch，configValue为参数值，0表示关，1表示开")
    @RequestMapping(value = "setUserConfig",method = RequestMethod.POST)
    @ResponseBody
    public String setUserConfig(@RequestBody Map params){
        try {
            String openId = params.get("openId").toString();
            OnlineUser onlineUser = this.getOnlineUser(openId);
            if(onlineUser == null){
                return setResult(false,2000,"用户不存在",null);
            }
            int userId = onlineUser.getUserId();
            params.put("userId",userId);
            List<UserConfig> userConfig = userConfigService.getUserConfig(params);
            UserConfig config = null;
            if(userConfig != null && userConfig.size() > 0){
                config = userConfig.get(0);
            }
            String configValue = (String) params.get("configValue");
            String configType = (String) params.get("configType");
            if(config != null){
                config.setConfigValue(configValue);
            }else{
                config = new UserConfig();
                config.setConfigValue(configValue);
                config.setConfigType(configType);
                config.setUserId(userId);
            }
            userConfigService.saveOrUpdateConfig(config);
            return setResult(true,200,"",null);
        }catch (Exception e){
            e.printStackTrace();
            return setResult(false,2000,e.getMessage(),null);
        }
    }


}
