package com.baiwen.business.service.impl;

import com.baiwen.business.mapper.IUserMissionMapper;
import com.baiwen.business.model.UserMission;
import com.baiwen.business.service.IUserMissionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 **/
@Service
@Data
public class UserMissionServiceImpl implements IUserMissionService {

    @Autowired
    private IUserMissionMapper userMissionMapper;
    
    @Override
    public List<UserMission> getUserMissionList(Map params) {
        if(params == null){
            return null;
        }
        List<UserMission> userMissionList = userMissionMapper.getUserMissionList(params);
        return userMissionList;
    }
}
