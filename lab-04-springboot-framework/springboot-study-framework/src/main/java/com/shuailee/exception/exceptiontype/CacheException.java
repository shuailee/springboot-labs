package com.shuailee.exception.exceptiontype;

import com.shuailee.model.result.ErrorCodeEnum;
import lombok.Getter;

/**
 * 缓存异常
 * */
public class CacheException extends RuntimeException {

    private static final long serialVersionUID = 3116483353040779859L;

    @Getter
    private int errorCode = ErrorCodeEnum.CACHE_CODE.getCode();

    public CacheException(String msg) {
        super(msg);
    }

    public CacheException(String msg, Throwable cause) {
        super(msg, cause);
    }


    public CacheException(Throwable cause) {
        super(cause);
    }

}