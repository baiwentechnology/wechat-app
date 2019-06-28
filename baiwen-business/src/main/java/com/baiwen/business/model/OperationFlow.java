package com.baiwen.business.model;

import com.baiwen.business.Enum.GoodsStatus;
import lombok.Data;

import java.util.Date;

/**
 * 操作流水
 */
@Data
public class OperationFlow {
    private int flowId;
    private int userId;
    private int businessId;
    private int goodsId;
    private int userGoodsId;
    private int flowType;
    private String content;
    private Date createTime;

    private int statusCode;
    private String statusName;

    public void setStatusCode(int code){
        this.statusCode = code;
        this.statusName = GoodsStatus.valueOfName(code);
    }
}
