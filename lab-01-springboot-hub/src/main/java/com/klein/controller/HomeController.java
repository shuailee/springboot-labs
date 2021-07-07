package com.klein.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    /**
     * 用户登录
     * @param request HttpServletRequest request,
     * @param username
     * @param password
     * @param session , HttpSession session
     * @return
     */
    @ApiOperation(value = "登录接口")
    @GetMapping(value = "/login")
    public String login( String username, String password) {
        //对密码进行加密
        //password =new SimpleHash("md5", password, ByteSource.Util.bytes(username.toLowerCase() + "shiro"),2).toHex();
        //如果有点击  记住我
        //UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password,remeberMe);
        // 根据用户名和密码生成一个token，登陆验证时使用此token作为凭据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            //登录验证
            subject.login(usernamePasswordToken);
            //User user=(User) subject.getPrincipal();
            //更新用户登录时间，也可以在ShiroRealm里面做
            //session.setAttribute("user", user);
            return "index";
        }catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
            return "用户名不存在！";
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            return "没有权限";
        }
        /*
        * //登录失败从request中获取shiro处理的异常信息 shiroLoginFailure:就是shiro异常类的全类名
            String exception = (String) request.getAttribute("shiroLoginFailure");
        * */

    }

    /**
     * 登出  这个方法没用到,用的是shiro默认的logout
     * @return
     */
    @ApiOperation(value = "登出接口")
    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "安全登出";
    }


    /*
    * @RequiresPermissions(权限)  需要特定权限才能访问
    * @RequiresRoles(角色)  需要特定角色才能访问
     * @RequiresAuthentication 需要认证才能访问
    *
    * */

    // 需要特定角色才能访问
    @RequiresRoles("超级管理员")
    @GetMapping("/admin")
    public String admin() {
        return "admin success!";
    }
    // 需要认证才能访问
    @RequiresAuthentication
    @GetMapping("/index")
    public String index() {
        return "index success!";
    }
    // 需要特定权限才能访问
    @RequiresPermissions("userinfo:add")
    @GetMapping("/add")
    public String add() {
        return "add success!";
    }


    @GetMapping("/query")
    public String query() {
        return "query success!";
    }

    @GetMapping("/error")
    public String error() {
        return "error!";
    }


}
