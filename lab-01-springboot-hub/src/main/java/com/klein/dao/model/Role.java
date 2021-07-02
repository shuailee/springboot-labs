package com.klein.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Long id;

    private String tenantId;

    private String roleName;

    private Boolean status;

    private Boolean isDeleted;

    private Date updatedTime;

    private Date createdTime;

    private String updatedUser;

    private String createdUser;

}