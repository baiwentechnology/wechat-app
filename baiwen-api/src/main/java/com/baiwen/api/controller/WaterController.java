package com.baiwen.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.baiwen.api.message.OnlineUser;
import com.baiwen.business.model.DayDrop;
import com.baiwen.business.model.User;
import com.baiwen.business.service.IDayDropService;
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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@Api(description = "浇水接口")
@RequestMapping(value = "water")
@ResponseBody
public class WaterController extends BaseController{

    @Autowired
    private IUserService userService;

    @Autowired
    private IDayDropService dayDropService;

    @ApiOperation(value = "给树浇水" ,  notes="传入openId,返回gapCount表示还差几次才能点金币，canGetGold是表示能否点击树获取金币")
    @RequestMapping(value = "setTreeWater",method = RequestMethod.POST)
    public String setTreeWater(@RequestBody Map params){
        try {
            String openId = params.get("openId").toString();
            OnlineUser onlineUser = this.getOnlineUser(openId);
            if (onlineUser == null) {
                return setResult(false, 2000, "用户不存在", null);
            }
            User user = userService.getUserByOpenId(openId);
            int userId = user.getUserId();
            Map param = new HashMap();
            param.put("userId",userId);
            String dayStr = DateUtils.convertToString(new Date(), "yyyy-MM-dd");
            DayDrop dayDrop = dayDropService.getUserDayDrop(param);
            if(dayDrop == null){
                dayDrop = new DayDrop();
                dayDrop.setAmCount(0);
                dayDrop.setPmCount(0);
                dayDrop.setUserId(userId);
                dayDrop.setDayStr(dayStr);
            }
            int amCount = dayDrop.getAmCount();
            int pmCount = dayDrop.getPmCount();
            GregorianCalendar ca = new GregorianCalendar();
            if(ca.get(GregorianCalendar.AM_PM)==0){
                if(amCount >= 5){
                    return setResult(false,2000,"上午浇水次数已满",null);
                }else{
                    amCount++;
                }
            }else{
                if(pmCount >= 5){
                    return setResult(false,2000,"下午浇水次数已满",null);
                }else{
                    pmCount++;
                }
            }
            dayDrop.setAmCount(amCount);
            dayDrop.setPmCount(pmCount);
            int treeWater = user.getTreeWater()+20;
            if(treeWater >100){
                treeWater = 100;
            }
            user.setTreeWater(treeWater);
            Map<String,Object> result = new HashMap<>();
            if(user.getWaterDrop() <= 0){
                result.put("isEmpty",true);
                return setResult(false,2000,"水壶已空，不能再浇水",result);
            }
            int waterDrop = user.getWaterDrop()-20;
            if(waterDrop < 0){
                waterDrop = 0;
            }
            user.setWaterDrop(waterDrop);
            userService.addOrUpdateUser(user);
            dayDropService.addOrUpdate(dayDrop);

            int gapCount = (100 - treeWater) / 20;
            boolean canGetGold = false;
            if(gapCount == 0){
                canGetGold = true;
            }
            result.put("gapCount",gapCount);
            result.put("canGetGold",canGetGold);
            result.put("isEmpty",false);
            return setResult(true,200,"成功",result);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return setResult(false,2000,e.getMessage(),null);
        }
    }

    @ApiOperation(value = "树的水满100则兑换成金币" ,  notes="传入openId,成功的话后台会直接把树的水量清除，加上3个金币,如果没满次数，则返回失败")
    @RequestMapping(value = "changeWaterToGold",method = RequestMethod.POST)
    public String changeWaterToGold(@RequestBody Map params){
        try {
            String openId = params.get("openId").toString();
            OnlineUser onlineUser = this.getOnlineUser(openId);
            if (onlineUser == null) {
                return setResult(false, 2000, "用户不存在", null);
            }
            User user = userService.getUserByOpenId(openId);
            int treeWater = user.getTreeWater();
            if(treeWater == 100){
                treeWater = 0;
            }else{
                return setResult(false, 2000, "还不能兑换成金币", null);
            }
            user.setTreeWater(treeWater);
            int gold = user.getGold();
            user.setGold(gold+3);
            userService.addOrUpdateUser(user);
            return setResult(true,200,"成功",null);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return setResult(false,2000,e.getMessage(),null);
        }
    }
}
