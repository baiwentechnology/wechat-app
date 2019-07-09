package com.baiwen.api.controller;

import com.baiwen.api.message.OnlineUser;
import com.baiwen.business.model.Mission;
import com.baiwen.business.model.UserMission;
import com.baiwen.business.service.IMissionService;
import com.baiwen.business.service.IUserMissionService;
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

import java.util.*;

/**
 *
 **/
@Controller
@Slf4j
@Api(description = "任务模块")
@RequestMapping(value = "mission")
@ResponseBody
public class MissionController extends BaseController{

    /*@Autowired
    private IMissionService missionService;

    @Autowired
    private IUserMissionService userMissionService;*/

    /*@RequestMapping(value = "/getUserMission",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户的任务" ,  notes="传入openId，返回任务列表")
    public String getUserMission(@RequestBody Map params){
        try {
            OnlineUser onlineUser = this.getOnlineUser(params.get("openId").toString());
            if(onlineUser.isOnLine()==false){
                return setResult(false, 2001, "请登录", null);
            }
            int userId = onlineUser.getUserId();
            String dayStr = DateUtils.convertToString(new Date(), "yyyy-MM-dd");
            List<Mission> missionList = missionService.getMissionList();
            if(missionList == null || missionList.size() == 0){
                return setResult(false,200,"无任务",null);
            }
            List<Integer> mIds = new ArrayList<>();
            for(Mission mission : missionList){
                mIds.add(mission.getId());
            }
            Map map = new HashMap();
            map.put("userId",userId);
            map.put("dayStr",dayStr);
            map.put("missionIds",mIds);
            List<UserMission> userMissionList = userMissionService.getUserMissionList(map);
            List<Integer> userMissionIds = new ArrayList<>();
            if(userMissionList != null && userMissionList.size() > 0){
                for(UserMission userMission : userMissionList){
                    userMissionIds.add(userMission.getMissionId());
                }
            }
            List<Map> result = new ArrayList<>();
            for(Mission mission : missionList){
                int missionId = mission.getId();
                if(userMissionIds.contains(missionId)){

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return setResult(false,2000,e.getMessage(),null);
        }
    }*/
}
