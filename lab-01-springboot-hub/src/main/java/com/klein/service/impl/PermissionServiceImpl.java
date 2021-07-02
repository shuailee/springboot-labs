package com.klein.service.impl;

import com.klein.dao.mapper.PermissionMapper;
import com.klein.dao.model.Permission;
import com.klein.dao.model.Role;
import com.klein.service.PermissionService;
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
    public Set<Permission> findPermissionsByRoleId(Set<Role> roles) {
        return permissionMapper.findPermissionsByRoleId(roles);
    }
}
