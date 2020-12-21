package com.shuailee.exception.exceptiontype;


import com.shuailee.model.result.ErrorCodeEnum;
import lombok.Getter;

/**
 * 数据源异常
 * */
public class DataSourceException extends RuntimeException{

    @Getter
    private int errorCode = ErrorCodeEnum.DATASOURCE_CODE.getCode();

    public DataSourceException(String msg) {
        super(msg);
    }

    public DataSourceException(String msg, Throwable cause) {
        super(msg, cause);
    }


    public DataSourceException(Throwable cause) {
        super(cause);
    }

}
