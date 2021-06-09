package com.klein.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @package: com.klein.dao
 * @description:
 * @author: klein
 * @date: 2021-06-09 18:12
 **/
@Data
@TableName(value = "User")//指定表名
public class User {
    /**
     * value与数据库主键列名一致，若实体类属性名与表主键列名一致可省略value
     * 指定自增策略
     * */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 若没有开启驼峰命名，或者表中列名不符合驼峰规则，可通过该注解指定数据库表中的列名，exist标明数据表中有没有对应列
     * */
    @TableField(value = "name",exist = true)
    private String name;
    private Integer age;
    private String email;
}
