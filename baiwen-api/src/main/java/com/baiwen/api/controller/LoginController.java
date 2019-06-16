package com.baiwen.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.baiwen.business.model.User;
import com.baiwen.business.service.IUserService;
import com.baiwen.common.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@Api(description = "登录主接口")
public class LoginController extends BaseController{
    //小程序appId
    private static final String WX_APPID = "wx6d4786908679cfe6";

    //小程序密钥
    private static final String WX_APPSERCET = "eb2fd75978e8767c0a59e990fa7afcd5";

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户登录接口" ,  notes="传入登录时js获取到的code，获取openId，去数据库里面查，如果存在则返回用户信息，如果不存在新增用户，返回初始化信息,并把openId放入session中")
    public String login(String code){
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
                    userService.addUser(user);
                }
                setUserToSession(user);
                return setResult(true,200,"",user);
            }else{
                return setResult(false, 2000, "登录出错", null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return setResult(false,2000,e.getMessage(),null);
        }
    }
}
