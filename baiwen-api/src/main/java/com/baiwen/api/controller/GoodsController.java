package com.baiwen.api.controller;

import com.baiwen.api.message.OnlineUser;
import com.baiwen.business.Enum.GoodsStatus;
import com.baiwen.business.model.Goods;
import com.baiwen.business.model.User;
import com.baiwen.business.model.UserGoods;
import com.baiwen.business.service.IGoodsService;
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

import java.util.Date;
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
            Map result = new HashMap();
            result.put("gold",user.getGold());
            result.put("waterDrop",user.getWaterDrop());
            result.put("goodsList",list);
            return setResult(true,200,"成功",result);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return setResult(false,2001,e.getMessage(),null);
        }
    }

    @RequestMapping(value = "exchangeGoods",method = RequestMethod.POST)
    @ApiOperation(value = "用户兑换商品" ,  notes="传入openId和goodsId(商品Id)")
    public String exchangeGoods(@RequestBody Map map){
        try {
            String openId = map.get("openId").toString();
            User user = userService.getUserByOpenId(openId);
            if (user == null) {
                return setResult(false, 2000, "用户不存在", null);
            }
            Long goodsId = (Long) map.get("goodsId");
            Goods goods = goodsService.getGoods(goodsId);
            if(goods == null){
                return setResult(false,2000,"商品不存在或已失效",null);
            }
            int gold = user.getGold();
            int price = goods.getPrice();
            if(gold < price){
                return setResult(false,2000,"金币不足",null);
            }
            gold = gold - price;
            user.setGold(gold);
            UserGoods userGoods = new UserGoods();
            convert(user, goods,userGoods);
            goodsService.addUserGoods(user,userGoods);
            return setResult(true,200,"成功",null);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return setResult(false,2001,"兑换失败",null);
        }
    }

    private void convert(User user,Goods goods,UserGoods userGoods){
        Date now = new Date();
        userGoods.setBusinessId(goods.getBusinessId());
        userGoods.setCreateTime(now);
        userGoods.setDeleted(false);
        userGoods.setDescribe(goods.getDescribe());
        Date expirationTime = DateUtils.addDays(now, goods.getExpirationDay());
        userGoods.setExpirationTime(expirationTime);
        userGoods.setGoodsId(goods.getId());
        userGoods.setGoodsName(goods.getGoodsName());
        userGoods.setStatusCode(GoodsStatus.NOTUSED.getCode());
        userGoods.setUpdateTime(now);
        userGoods.setUrl(goods.getUrl());
        userGoods.setUserId(user.getUserId());
        userGoods.setUserName(user.getUserName());
    }
}
