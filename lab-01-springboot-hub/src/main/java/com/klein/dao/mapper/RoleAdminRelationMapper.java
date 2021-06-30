package com.klein.dao.mapper;

import com.klein.dao.model.RoleAdminRelation;

public interface RoleAdminRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleAdminRelation record);

    int insertSelective(RoleAdminRelation record);

    RoleAdminRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleAdminRelation record);

    int updateByPrimaryKey(RoleAdminRelation record);
}