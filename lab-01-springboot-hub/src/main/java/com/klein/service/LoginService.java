package com.klein.service;


import com.klein.dao.model.User;

public interface LoginService{

    User getUserByName(String userName);
}
