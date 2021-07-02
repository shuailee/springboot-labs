package com.klein.service.impl;

import com.klein.dao.mapper.PermissionMapper;
import com.klein.dao.model.Permission;
import com.klein.dao.model.Role;
import com.klein.dto.PermissionDTO;
import com.klein.dto.RoleDTO;
import com.klein.service.PermissionService;
import com.klein.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @package: com.klein.service.impl
 * @description:
 * @author: klein
 * @date: 2021-07-02 13:19
 **/
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public Set<PermissionDTO> findPermissionsByRoleId(Set<RoleDTO> roleDTOS) {
        Set<Role> roles = BeanUtil.copyObjects(roleDTOS,Role.class);
        Set<Permission> permissions = permissionMapper.findPermissionsByRoleId(roles);
        return BeanUtil.copyObjects(permissions,PermissionDTO.class);
    }
}
