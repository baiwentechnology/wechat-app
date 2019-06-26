package com.baiwen.business.Enum;

/**
 * 用户商品使用状态
 */
public enum GoodsStatus {

    NOTUSED(0,"未使用"),
    INUSED(1,"使用中"),
    USED(2,"已使用");

    private int code;
    private String name;

    GoodsStatus(int code,String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
