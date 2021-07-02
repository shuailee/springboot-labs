package com.klein.service.impl;

import com.klein.dao.mapper.RoleMapper;
import com.klein.dao.model.Role;
import com.klein.dto.RoleDTO;
import com.klein.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<RoleDTO> findRolesByUserId(Long uid) {
        Set<Role> roles = roleMapper.findRolesByUserId(uid);
        Set<RoleDTO> roleDTOS = roles.stream().map(s -> {
            RoleDTO roleDTO = new RoleDTO();
            BeanUtils.copyProperties(s, roleDTO);
            return roleDTO;
        }).collect(Collectors.toSet());
        return roleDTOS;
    }
}
