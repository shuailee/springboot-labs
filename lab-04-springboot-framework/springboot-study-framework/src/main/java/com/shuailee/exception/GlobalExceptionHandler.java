package com.shuailee.exception;

import com.shuailee.exception.exceptiontype.ApiException;
import com.shuailee.exception.exceptiontype.AppException;
import com.shuailee.exception.exceptiontype.FacadeException;
import com.shuailee.model.result.DataResult;
import com.shuailee.model.result.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常捕获
 * */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private HttpServletResponse response;

    /**
     * 注解校验 异常捕获
     * */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public DataResult<?> bindErrorHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.error("system error", e);
        return DataResult.fail(ErrorCodeEnum.SYS_CODE.getCode(), e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(value = FacadeException.class)
    public DataResult<?> facadeExceptionHandler(FacadeException e){
        log.error("facade error", e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return DataResult.fail(e.getErrorCode(), e.getMessage());
    }


    @ExceptionHandler(value = AppException.class)
    public DataResult<?> appExceptionHandler(AppException e){
        log.error("app error", e);
        return DataResult.fail(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = ApiException.class)
    public DataResult<?> apiExceptionHandler(ApiException e){
        log.error("api error", e);
        return DataResult.fail(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public DataResult<?> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        log.error("system error", e);
        return DataResult.fail(ErrorCodeEnum.SYS_CODE.getCode(), e.getMessage());
    }

}
