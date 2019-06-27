package com.baiwen.api.controller;

import com.baiwen.api.message.OnlineUser;
import com.baiwen.business.model.Goods;
import com.baiwen.business.model.User;
import com.baiwen.business.service.IGoodsService;
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
@Api(description = "商品接口")
@RequestMapping(value = "goods")
@ResponseBody
public class GoodsController extends BaseController{

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "getGoodsList",method = RequestMethod.POST)
    @ApiOperation(value = "获得所有商品列表" ,  notes="传入openId")
    public String getGoodsList(@RequestBody Map map){
        try {
            String openId = map.get("openId").toString();
            User user = userService.getUserByOpenId(openId);
            if (user == null) {
                return setResult(false, 2000, "用户不存在", null);
            }
            Map param = new HashMap();
            List<Goods> goodsList = goodsService.getGoodsList(param);
            List<Map<String, Object>> list = convertList(goodsList);
            return setResult(true,200,"成功",list);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return setResult(false,2001,e.getMessage(),null);
        }
    }
}
