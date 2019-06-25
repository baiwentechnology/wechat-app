package com.baiwen.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.baiwen.business.model.User;
import com.baiwen.business.model.UserConfig;
import com.baiwen.business.service.IUserConfigService;
import com.baiwen.business.service.IUserService;
import com.baiwen.common.util.JsonUtil;
import com.baiwen.common.util.MapUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@Api(description = "登录主接口")
@ResponseBody
public class LoginController extends BaseController{
    //小程序appId
    //private static final String WX_APPID = "wx6d4786908679cfe6";
    private static final String WX_APPID = "wxc3fe7a5234f6138e";
    //小程序密钥
    //private static final String WX_APPSERCET = "49f4d90c2ca812cfa5da685d6679e5c6";
    private static final String WX_APPSERCET = "17ea3ae8f8e037f99b0ad55b365faebf";

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserConfigService userConfigService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "用户登录接口" ,  notes="传入登录时js获取到的code，获取openId，去数据库里面查，如果存在则返回用户信息，如果不存在新增用户，返回初始化信息,并把openId放入session中")
    public String login(@RequestBody Map params){
        String code = (String) params.get("code");
        log.info(code);
        //微信的登录接口
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+WX_APPID+
                "&secret="+WX_APPSERCET+"&js_code="+ code +"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        try {
            //进行网络请求,访问url接口
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            //根据返回值进行后续操作
            if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK){
                String sessionData = responseEntity.getBody();
                JSONObject json = JsonUtil.getJSONFromString(sessionData);
                log.info(json.toJSONString());
                //判断是否请求出错
                if(json.containsKey("errcode")){
                    return setResult(false, 2000, "登录出错", null);
                }
                String openId = (String) json.get("openid");
                String sessionKey = (String) json.get("session_key");
                //判断openID是否存在数据库中，没有的话新增并返回初始值
                User user = userService.getUserByOpenId(openId);
                if(user == null){
                    user = new User();
                    user.setOpenId(openId);
                    user.setGold(0);
                    user.setWaterDrop(0);
                    user.setItemCount(0);
                    user.setTreeWater(0);
                    userService.addOrUpdateUser(user);
                    user.setMusicSwitch("1");
                }else{
                    Map param = new HashMap();
                    param.put("userId",user.getUserId());
                    param.put("configType","musicSwitch");
                    List<UserConfig> userConfig = userConfigService.getUserConfig(param);
                    if(userConfig != null && userConfig.size() >0){
                        UserConfig config = userConfig.get(0);
                        String configValue = config.getConfigValue();
                        user.setMusicSwitch(configValue);
                    }else{
                        user.setMusicSwitch("1");
                    }
                }
                putUserToSession(user);
                Map map = (Map) MapUtils.beanToMap(user);
                map.put("sessionKey",sessionKey);
                return setResult(true,200,"",map);
            }else{
                return setResult(false, 2000, "登录出错", null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return setResult(false,2000,e.getMessage(),null);
        }
    }
}
