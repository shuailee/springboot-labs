package com.klein.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;

    private String tenantId;

    private String userCode;

    private String userName;

    private String userPassword;

    private String userNickName;

    private String phone;

    private String email;

    private Boolean status;

    private Boolean isDeleted;

    private Date updatedTime;

    private Date createdTime;

    private String updatedUser;

    private String createdUser;


}