package com.baiwen.business.model;

import lombok.Data;

/**
 *
 **/
@Data
public class UserMission {
    private int id;

    private int userId;

    private int missionId;

    //日期
    private String dayStr;
}
