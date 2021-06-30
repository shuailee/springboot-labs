package com.klein.service.impl;


import com.klein.dao.model.Access;
import com.klein.dao.model.Role;
import com.klein.dao.model.User;
import com.klein.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public User getUserByName(String getMapByName) {
        return getMapByName(getMapByName);
    }

    /**
     * 模拟数据库查询
     *
     * @param userName 用户名
     * @return User
     */
    private User getMapByName(String userName) {
//        Access permissions1 = new Access("1", "query");
//        Access permissions2 = new Access("2", "add");
//        Set<Access> permissionsSet = new HashSet<>();
//        permissionsSet.add(permissions1);
//        permissionsSet.add(permissions2);
//        Role role = new Role("1", "admin", permissionsSet);
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(role);
//        User user = new User(1L, "wsl", "123456", roleSet);
//        Map<String, User> map = new HashMap<>();
//        map.put(user.getUserName(), user);
//
//        Set<Access> permissionsSet1 = new HashSet<>();
//        permissionsSet1.add(permissions1);
//        Role role1 = new Role("2", "user", permissionsSet1);
//        Set<Role> roleSet1 = new HashSet<>();
//        roleSet1.add(role1);
//        User user1 = new User(2L, "zhangsan", "123456", roleSet1);
//        map.put(user1.getUserName(), user1);
//        return map.get(userName);
        return new User();
    }
}
