package com.shuailee.exception.exceptiontype;


import com.shuailee.model.result.ErrorCodeEnum;
import lombok.Getter;

public class SysException extends RuntimeException {

    private static final long serialVersionUID = 3116483353040779859L;

    @Getter
    private int errorCode = ErrorCodeEnum.SYS_CODE.getCode();

    public SysException(String msg) {
        super(msg);
    }

    public SysException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SysException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public SysException(Throwable cause) {
        super(cause);
    }

}