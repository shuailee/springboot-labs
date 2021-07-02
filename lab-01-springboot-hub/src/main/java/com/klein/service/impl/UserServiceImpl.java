package com.klein.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.klein.dao.mapper.UserMapper;
import com.klein.dao.model.User;
import com.klein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        return userMapper.selectList(null);
    }

    @Override
    public User getUserByName(String username) {
        QueryWrapper queryWrapper = new QueryWrapper<User>().eq("user_name",username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public int insert(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int del(String username) {
        QueryWrapper queryWrapper = new QueryWrapper<User>().eq("user_name",username);
        return userMapper.delete(queryWrapper);
    }


}
