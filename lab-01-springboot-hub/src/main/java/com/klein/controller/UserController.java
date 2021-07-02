package com.klein.controller;

import com.klein.dao.model.User;
import com.klein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package: com.klein.controller
 * @description:
 * @author: klein
 * @date: 2021-07-02 13:39
 **/
@RestController
@RequestMapping("userInfo")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 创建固定写死的用户
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public String add() {
        User user = new User();
        user.setUserName("klein");
        user.setPhone("17777777777");
        user.setUserPassword("123456");
        userService.insert(user);
        return "创建用户成功";
    }

    /**
     * 删除固定写死的用户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    @ResponseBody
    public String del(Model model) {
        return userService.del("klein") > 0 ? "del success" : "del failer";
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @ResponseBody
    public String view(Model model) {
        return "这是用户列表页";
    }
}
