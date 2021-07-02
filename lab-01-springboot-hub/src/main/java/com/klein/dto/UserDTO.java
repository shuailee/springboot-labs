package com.klein.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
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

    private Set<RoleDTO> roles = new HashSet<>();

}