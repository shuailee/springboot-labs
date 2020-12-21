package com.shuailee.exception.exceptiontype;

import lombok.Getter;
import lombok.Setter;

public class AppException extends RuntimeException {

    private static final long serialVersionUID = 8688953989589840707L;

    @Getter
    @Setter
    private int errorCode;

    public AppException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }



}