package com.klein.service;


import com.klein.dto.PermissionDTO;
import com.klein.dto.RoleDTO;

import java.util.Set;

/**
 * @package: com.klein.service
 * @description:
 * @author: klein
 * @date: 2021-07-02 13:17
 **/
public interface PermissionService {
    Set<PermissionDTO> findPermissionsByRoleId(Set<RoleDTO> roles);
}
