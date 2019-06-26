package com.baiwen.business.model;

import lombok.Data;

@Data
public class User {
    private int userId;
    private String openId;
    private String mobile;
    private String userName;
    private int waterDrop;
    private int gold;
    private int itemCount;

    private String musicSwitch;

    private int treeWater;

    private int userType;
    //是否授权
    private boolean isAutho;
}
