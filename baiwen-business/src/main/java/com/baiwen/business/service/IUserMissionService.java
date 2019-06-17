package com.baiwen.business.service;

import com.baiwen.business.model.UserMission;

import java.util.List;
import java.util.Map;

/**
 * 用户任务
 **/
public interface IUserMissionService {

    List<UserMission> getUserMissionList(Map params);
}
