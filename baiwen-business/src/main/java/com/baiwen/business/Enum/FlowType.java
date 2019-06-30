package com.baiwen.business.Enum;

/**
 * 流水类型
 */
public enum FlowType {
    USEGOODS(0,"使用商品"),
    CONFIRM(1,"确认使用");

    private int code;
    private String name;

    FlowType(int code,String name){
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
        FlowType[] types = values();
        String returnName = "";
        for(FlowType type : types){
            if(code == type.code){
                returnName =  type.getName();
            }
        }
        return returnName;
    }
}
