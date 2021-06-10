package com.klein.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.klein.Lab01SpringbootHubApplicationTests;
import com.klein.dao.model.User;
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
 * @date: 2021-06-09 18:14
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap() {
        System.out.println(("----- selectByMap method test ------"));
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("id", "1");//写表中的列名
        List<User> userList = userMapper.selectByMap(columnMap);
        Assert.assertEquals(1, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testSelectBatchIds() {
        System.out.println(("----- selectBatchIds method test ------"));
        List<Integer> idList = Arrays.asList(1, 2, 3);
        List<User> userList = userMapper.selectBatchIds(idList);
        userList.forEach(System.out::println);
    }


    @Test
    public void testSelectPage() {

        System.out.println(("----- selectList method process ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);

        System.out.println(("----- selectPage method test ------"));

        // 内存分页
        // Page<User> userPage = userMapper.selectPage(new Page<>(1, 2), null);
        // 使用QueryWrapper条件构造器进行分页，物理分页
        Page<User> userPage = userMapper.selectPage(new Page<User>(1, 2),
                new QueryWrapper<User>()
                        .between("age", 18, 50)
                        .in("id", Arrays.asList(1, 2, 3, 4, 5))
        );

        List<User> userPageList = userPage.getRecords();
        userPageList.forEach(System.out::println);
    }

}