package com.klein.dao.mapper;

import com.klein.dao.model.Access;

public interface AccessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Access record);

    int insertSelective(Access record);

    Access selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Access record);

    int updateByPrimaryKey(Access record);
}