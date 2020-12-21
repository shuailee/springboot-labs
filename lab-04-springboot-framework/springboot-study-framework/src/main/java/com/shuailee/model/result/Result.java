package com.shuailee.model.result;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8886624063083045314L;

    protected Result() {
        super();
    }

    protected Result(T data) {
        this.setData(data);
    }

    private int code;

    private String msg;

    private T data;

    private Long timestamp = System.currentTimeMillis();

}
