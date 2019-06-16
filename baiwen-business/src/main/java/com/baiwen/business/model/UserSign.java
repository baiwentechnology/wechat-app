package com.baiwen.business.model;

import lombok.Data;

/**
 * 用户签到中间表
 */
@Data
public class UserSign {
    private Integer userId;
    /**
     * 签到日期 String yyyy-MM-dd
     */
    private String signDate;
}
