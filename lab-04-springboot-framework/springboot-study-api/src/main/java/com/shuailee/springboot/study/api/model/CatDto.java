package com.shuailee.springboot.study.api.model;

import lombok.Data;

/**
 * @program: springboot-study
 * @description: CatDto
 * @author: shuai.li
 * @create: 2020-06-10 16:23
 **/
@Data
public class CatDto {
    //商品编号
    private String goodId;
    //商品名称
    private String goodName;
    //商品价格
    private double goodPrice;
    //类型名称
    private String typeName;
}
