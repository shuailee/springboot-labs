package com.klein.dao.mapper;

import com.klein.dao.model.Permission;
import com.klein.dao.model.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


/**
 * @package: com.klein.dao.mapper
 * @description:
 * @author: klein
 * @date: 2021-07-02 17:55
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class PermissionMapperTest {

    @Autowired
    PermissionMapper permissionMappe;
    @Test
    public void findPermissionsByRoleId() {
        Set<Role> roleList=new HashSet<Role>(){{
            Role role= new Role();
            role.setId(1l);
            add(role);
        }};
        Set<Permission>  res =permissionMappe.findPermissionsByRoleId(roleList);
        Assert.assertNotNull(res);
    }

    @Test
    public void findPermissionsByRole() {

        Permission res =permissionMappe.selectByPrimaryKey(1l);
        Assert.assertNotNull(res);
    }
}