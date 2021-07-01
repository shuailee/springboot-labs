package com.klein.dao.mapper;

import com.klein.dao.model.PermissionRoleRelation;

public interface PermissionRoleRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PermissionRoleRelation record);

    int insertSelective(PermissionRoleRelation record);

    PermissionRoleRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PermissionRoleRelation record);

    int updateByPrimaryKey(PermissionRoleRelation record);
}