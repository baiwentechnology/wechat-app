package com.baiwen.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.baiwen.business.service.IsignService;
import com.baiwen.business.service.IsysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Slf4j
@Api(description = "系统配置")
@RequestMapping(value = "sys")
@ResponseBody
public class SystemController extends BaseController{
        @Autowired
        private IsysService isysService;

        @ApiOperation(value = "系统公告" ,  notes="暂定系统公告的请求sysId为1,返回数据进行解析")
        @RequestMapping(value = "/{sysId}",method = RequestMethod.GET)
        public String getAnnouncement(@PathVariable int sysId){
            try {
                JSONObject jsonObject = isysService.getSysConfigById(sysId);
                return setResult(true, 200, "", jsonObject);
            }catch (Exception e){
                e.getMessage();
                return setResult(false, 2000, "", "请求失败，请联系管理员");

            }

    }
}
