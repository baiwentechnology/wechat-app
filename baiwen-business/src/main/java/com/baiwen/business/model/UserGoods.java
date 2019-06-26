package com.baiwen.business.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserGoods {
    private int id;
    private int userId;
    private int goodsId;
    private int status;
    private Date createTime;
    private Date updateTime;
    private String userName;
}
