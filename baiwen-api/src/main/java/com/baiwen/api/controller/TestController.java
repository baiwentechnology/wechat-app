package com.baiwen.api.controller;

import com.baiwen.business.service.ItestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/test")
public class TestController {
@Autowired
private ItestService itestService;

    @RequestMapping("/test1")
    @ResponseBody
    private String getTest(){
       Integer getTest= itestService.getTest();
        return "1";
    }

}
