package com.shuailee.exception.exceptiontype;


import lombok.Getter;
import lombok.Setter;

/**
 * 对内业务异常
 * */
public class FacadeException extends RuntimeException {

    @Setter
    @Getter
    private int errorCode;

    public FacadeException() {
        super();
    }

    public FacadeException(String message) {
        super(message);
    }

    public FacadeException(Throwable cause) {
        super(cause);
    }

    public FacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacadeException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

}
