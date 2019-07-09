package com.baiwen.business.model;

import com.baiwen.business.Enum.GoodsStatus;
import lombok.Data;

import java.util.Date;

@Data
public class UserGoods {
    private int id;
    private int userId;
    private int goodsId;
    private int statusCode;
    private Date createTime;
    private Date updateTime;
    private String userName;
    private String goodsName;
    private int businessId;
    //过期时间
    private Date expirationTime;
    private boolean deleted;

    private String describe;

    private String url;

    private String statusName;

    public void setStatusCode(int code){
        this.statusCode = code;
        this.statusName = GoodsStatus.valueOfName(code);
    }
}
