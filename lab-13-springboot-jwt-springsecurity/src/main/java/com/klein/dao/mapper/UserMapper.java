package com.klein.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.klein.dao.model.Role;
import com.klein.dao.model.User;

public interface UserMapper extends BaseMapper<User> {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}