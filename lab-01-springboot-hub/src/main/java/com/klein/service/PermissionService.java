package com.klein.service;

import com.klein.dao.model.Permission;
import com.klein.dao.model.Role;

import java.util.Set;

/**
 * @package: com.klein.service
 * @description:
 * @author: klein
 * @date: 2021-07-02 13:17
 **/
public interface PermissionService {
    Set<Permission> findPermissionsByRoleId(Set<Role> roles);
}
