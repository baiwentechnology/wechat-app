package com.baiwen.business.model;

import lombok.Data;

import java.util.Date;

@Data
public class Goods {
    private int id;
    private String goodsName;
    private String goodsCategory;
    private String url;
    private int stock;
    private int price;
    private int businessId;
    private boolean deleted;
    private Date createTime;
    private Date updateTime;
    private int expirationDay;
    private String describe;
    private String address;
}
