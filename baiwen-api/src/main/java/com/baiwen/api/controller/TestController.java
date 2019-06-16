package com.baiwen.api.controller;

import com.baiwen.business.service.ItestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
@Api(description = "测试接口")
@RequestMapping(value = "/test")
public class TestController {
@Autowired
private ItestService itestService;

    @ApiOperation(value = "新增用户" ,  notes="新增注册")
    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    @ResponseBody
    private String getTest(){
       Integer getTest= itestService.getTest();
        return "1";
    }

}
