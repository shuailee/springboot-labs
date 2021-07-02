package com.klein.service.impl;

import com.klein.dao.mapper.RoleMapper;
import com.klein.dao.model.Role;
import com.klein.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @package: com.klein.service.impl
 * @description:
 * @author: klein
 * @date: 2021-07-02 11:16
 **/
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public Set<Role> findRolesByUserId(Long uid) {
        return roleMapper.findRolesByUserId(uid);
    }
}
