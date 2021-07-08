package com.klein.service;

import com.klein.dto.RoleDTO;

import java.util.Set;

/**
 * @package: com.klein.service
 * @description:
 * @author: klein
 * @date: 2021-07-02 11:15
 **/
public interface RoleService {

    Set<RoleDTO> findRolesByUserId(Long uid);
}
