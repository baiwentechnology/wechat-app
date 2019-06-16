package com.baiwen.business.model;

import lombok.Data;

@Data
public class UserConfig {

    private int id;

    private String configType;

    private String configValue;

    private int userId;
}
