package com.klein.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.klein.dao.model.Permission;
import com.klein.dao.model.Role;
import org.apache.ibatis.annotations.Param;
import java.util.Set;

/**
 * 使用mybatisplus 结合xml配置
 * */
public interface PermissionMapper extends BaseMapper<Permission> {

    Set<Permission> findPermissionsByRoleId(@Param("roles") Set<Role> roles);

}