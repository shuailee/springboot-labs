package com.klein.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.klein.dao.model.Permission;
import com.klein.dao.model.Role;
import com.klein.dao.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.Set;
// extends BaseMapper<Permission>
public interface PermissionMapper  {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);


    Set<Permission> findPermissionsByRoleId(@Param("roles") Set<Role> roles);
}