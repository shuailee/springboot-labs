package com.shuailee.springboot.study.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @program: springboot-study
 * @description: Cat
 * @author: shuai.li
 * @create: 2020-06-10 16:23
 **/
@Data
public class Cat {
    //商品编号
    @JsonProperty("GoodId")
    private String GoodId;
    //商品名称
    private String GoodName;
    //商品价格
    private double goodPrice;
    //类型名称
    private String typeName;
}
