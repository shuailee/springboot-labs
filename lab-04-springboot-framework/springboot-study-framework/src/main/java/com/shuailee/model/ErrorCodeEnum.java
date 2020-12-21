package com.shuailee.model.result;

/**
 * @program: geek-calendar-framework
 * @description: ErrorCodeEnum
 * @author: shuai.li
 * @create: 2020-05-21 17:49
 **/
public enum ErrorCodeEnum {

    SYS_CODE(50000,"系统异常"),
    DATASOURCE_CODE(50001,"数据源异常"),
    CACHE_CODE(50002,"缓存异常");


    private Integer code;
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
