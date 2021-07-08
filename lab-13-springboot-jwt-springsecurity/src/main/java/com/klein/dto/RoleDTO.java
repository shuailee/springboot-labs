package com.klein.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;

    private String tenantId;

    private String roleName;

    private Boolean status;

    private Boolean isDeleted;

    private Date updatedTime;

    private Date createdTime;

    private String updatedUser;

    private String createdUser;

    private Set<PermissionDTO> permissionDTOS = new HashSet<>();
}