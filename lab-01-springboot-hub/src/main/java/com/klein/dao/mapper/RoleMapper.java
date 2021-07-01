package com.klein.dao.mapper;

import com.klein.dao.model.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     *  根据用户id查询用户角色信息
     * 会默认应用xml文件的resultmap
     * */
//    @ResultMap("GlMemberDept")
    @Select("SELECT r.* from role r LEFT JOIN role_user_relation ur on r.id = ur.role_id where ur.user_id  = #{user_id, jdbcType=INTEGER}")
    Set<Role> findRolesByUserId(@Param("user_id") Long userId);
}