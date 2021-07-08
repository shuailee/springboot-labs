package com.klein.controller;

import com.klein.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @package: com.klein.controller
 * @description:
 * @author: klein
 * @date: 2021-06-09 16:33
 **/


@Api(tags = {"权限管理"})
@Slf4j
@RestController
public class HomeController {

    @Autowired
    private JwtTokenUtil jwtToken;

    @ApiOperation(value = "登录接口")
    @GetMapping(value = "/login")
    public String login(String username, String password) {
        // 1. 根据登录名从数据库查询用户,验证用户名和密码，为了简单演示jwt，这里假设验证通过
        // Todo
        // 2. 验证成功生成token，并返回
        String token = jwtToken.generateToken(username);
        return token;

    }


    @GetMapping("/getuserinfo")
    public String getUserInfo(@RequestHeader("Authorization") String authHeader) {
        // 黑名单token
        List<String> blacklistToken = Arrays.asList("禁止访问的token");
        Claims claims = jwtToken.getClaimByToken(authHeader);
        if (claims == null || jwtToken.isTokenExpired(claims.getExpiration()) || blacklistToken.contains(authHeader)) {
            return "token 不可用";
        }
        String userId = claims.getSubject();
        // 根据用户id获取接口数据返回接口
        return userId;

    }




}
