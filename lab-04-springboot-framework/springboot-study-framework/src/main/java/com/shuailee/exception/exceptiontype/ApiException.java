package com.shuailee.exception.exceptiontype;

import com.shuailee.utils.Checker;
import lombok.Getter;

/**
 * @program: geek-calendar-framework
 * @description: ApiException
 * @author: shuai.li
 * @create: 2020-05-21 18:48
 **/
public class ApiException extends RuntimeException {

    @Getter
    private int errorCode;
    @Getter
    private String errorMessage;

    /**
     * @param errorCode 此Code必须在契约中有明确定义,不允许硬编码
     * @param errorMessage
     */
    public ApiException(int errorCode, String errorMessage) {
        // Checker.isNotNull(errorCode, "errorCode");
        Checker.isNotEmpty(errorMessage, "errorMessage");
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }



}
