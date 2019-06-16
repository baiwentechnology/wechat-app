package com.baiwen.api.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    /**
     * 是否成功
     */
    private boolean flag;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    public Result(boolean flag, Integer code, String message, Object
            data) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Result() {
    }
    public Result(boolean flag, Integer code, String message) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
