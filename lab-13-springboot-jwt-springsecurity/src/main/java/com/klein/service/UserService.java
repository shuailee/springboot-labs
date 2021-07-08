package com.klein.service;

import com.klein.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUser();

    UserDTO getUserByName(String userName);

    int insert(UserDTO user);

    int del(String username);
}
