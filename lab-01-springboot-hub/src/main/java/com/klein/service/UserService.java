package com.klein.service;


import com.klein.dao.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUser();

    User getUserByName(String userName);

    int insert(User user);

    int del(String username);
}
