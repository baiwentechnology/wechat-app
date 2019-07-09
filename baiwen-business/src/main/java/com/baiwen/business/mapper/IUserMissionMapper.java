package com.baiwen.business.mapper;

import com.baiwen.business.model.UserMission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 **/
@Repository
public interface IUserMissionMapper {

    List<UserMission> getUserMissionList(Map params);
}
