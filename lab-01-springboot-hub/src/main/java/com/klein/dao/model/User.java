package com.klein.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @package: com.klein.dao
 * @description:
 * @author: klein
 * @date: 2021-06-09 18:12
 **/
@Data
//指定表名
@TableName(value = "User")
@AllArgsConstructor
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
    @TableField(value = "userName",exist = true)
    private String userName;
    private String password;
    /**
     * 用户对应的角色集合
     */
    private Set<Role> roles;
}
