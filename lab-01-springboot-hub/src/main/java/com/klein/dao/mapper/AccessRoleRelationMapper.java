package com.klein.dao.mapper;

import com.klein.dao.model.AccessRoleRelation;

public interface AccessRoleRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccessRoleRelation record);

    int insertSelective(AccessRoleRelation record);

    AccessRoleRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccessRoleRelation record);

    int updateByPrimaryKey(AccessRoleRelation record);
}