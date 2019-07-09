package com.baiwen.business.Enum;

/**
 * 用户商品使用状态
 */
public enum GoodsStatus {

    NOTUSED(0,"未使用"),
    INUSED(1,"待确认"),
    USED(2,"已使用"),
    EXPIRE(3,"已过期");

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

    public static String valueOfName(int code){
        GoodsStatus[] goodsStatuses = values();
        String returnName = "";
        for(GoodsStatus status : goodsStatuses){
            if(code == status.code){
                returnName =  status.getName();
            }
        }
        return returnName;
    }
}
