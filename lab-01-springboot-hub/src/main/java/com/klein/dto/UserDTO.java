package com.klein.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户对象")
public class UserDTO {
    @ApiModelProperty(value = "主键id")
    private Long id;
    @ApiModelProperty(value = "租户id")
    private String tenantId;
    @ApiModelProperty(value = "用户编号")
    private String userCode;
    @ApiModelProperty(value = "用户名", required = true,position = 1)
    private String userName;
    @ApiModelProperty(value = "用户密码", required = true,position = 2)
    private String userPassword;
    @ApiModelProperty(value = "用户昵称")
    private String userNickName;

    private String phone;

    private String email;
    @ApiModelProperty(value = "状态")
    private Boolean status;

    private Boolean isDeleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    private String updatedUser;

    private String createdUser;

    private Set<RoleDTO> roles = new HashSet<>();

}