package com.baiwen.business.model;

import lombok.Data;

/**
 * 系统配置
 */
@Data
public class SysConfig {
    /**
     * 系统配置
     */
    private Integer id;
    /**
     * 类型
     */
    private int configType;
    /**
     * 值
     */
    private String configValue;
}
