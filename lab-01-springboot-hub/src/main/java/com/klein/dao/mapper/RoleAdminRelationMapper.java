package com.klein.dao.mapper;

import com.klein.dao.model.RoleAdminRelation;

public interface RoleAdminRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleAdminRelation record);

    int insertSelective(RoleAdminRelation record);

    RoleAdminRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleAdminRelation record);

    int updateByPrimaryKey(RoleAdminRelation record);
}