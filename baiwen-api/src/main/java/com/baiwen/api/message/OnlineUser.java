package com.baiwen.api.message;

import lombok.Data;

/**
 * 缓存用户信息的实体类
 */
@Data
public class OnlineUser {
    private int userId;
    private String openId;
    private String mobile;
    private String userName;
    private int waterDrop;
    private int gold;
    private int itemCount;
    private boolean onLine;

    public OnlineUser(boolean onLine) {
        this.onLine = onLine;
    }

    public OnlineUser() {
    }
}
