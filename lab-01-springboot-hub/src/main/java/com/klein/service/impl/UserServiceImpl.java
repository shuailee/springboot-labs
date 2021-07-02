package com.klein.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.klein.dao.mapper.UserMapper;

import com.klein.dao.model.User;
import com.klein.dto.UserDTO;
import com.klein.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userMapper.selectList(null);
        List<UserDTO> userDTOS = users.stream().map(s -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(s, userDTO);
            return userDTO;
        }).collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public UserDTO getUserByName(String username) {
        QueryWrapper queryWrapper = new QueryWrapper<UserDTO>().eq("user_name", username);
        User user = userMapper.selectOne(queryWrapper);
        UserDTO UserDTO = new UserDTO();
        BeanUtils.copyProperties(user, UserDTO);
        return UserDTO;
    }

    @Override
    public int insert(UserDTO UserDTO) {
        User user = new User();
        BeanUtils.copyProperties(UserDTO, user);
        return userMapper.insertSelective(user);
    }

    @Override
    public int del(String username) {
        QueryWrapper queryWrapper = new QueryWrapper<UserDTO>().eq("user_name", username);
        return userMapper.delete(queryWrapper);
    }


}
