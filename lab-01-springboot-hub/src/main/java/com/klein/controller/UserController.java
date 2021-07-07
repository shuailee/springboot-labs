package com.klein.controller;

import com.klein.dto.UserDTO;
import com.klein.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @package: com.klein.controller
 * @description:
 * @author: klein
 * @date: 2021-07-02 13:39
 **/
@Api(tags = {"用户管理"})
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
    @ApiOperation(value = "新增用户")
    public String add(UserDTO userDTO) {
        UserDTO UserDTO = new UserDTO();
        UserDTO.setUserName("klein");
        UserDTO.setPhone("17777777777");
        UserDTO.setUserPassword("123456");
        userService.insert(UserDTO);
        return "创建用户成功";
    }

    /**
     * 删除固定写死的用户
     *
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除用户")
    public String del(UserDTO userDTO) {
        return userService.del("klein") > 0 ? "del success" : "del failer";
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "用户列表")
    public List<UserDTO> view() {
        return new ArrayList<>();
    }
}
