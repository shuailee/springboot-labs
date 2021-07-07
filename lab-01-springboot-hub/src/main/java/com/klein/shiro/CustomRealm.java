package com.klein.shiro;

import com.klein.dao.model.Permission;
import com.klein.dao.model.Role;
import com.klein.dao.model.User;
import com.klein.dto.PermissionDTO;
import com.klein.dto.RoleDTO;
import com.klein.dto.UserDTO;
import com.klein.service.PermissionService;
import com.klein.service.RoleService;
import com.klein.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    /**
     * @MethodName doGetAuthenticationInfo
     * @Description 认证配置类,身份认证/登录(账号密码验证)。
     * @Param [authenticationToken]
     * @Return AuthenticationInfo
     * @Author WangShiLin
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (StringUtils.isEmpty(authenticationToken.getPrincipal())) {
            return null;
        }

        //获取用户名密码 第一种方式
        //String username = (String) authenticationToken.getPrincipal(); //主体
        //String password = new String((char[]) authenticationToken.getCredentials()); //凭证

        //获取用户名 密码 第二种方式，使用UsernamePasswordToken对象
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());

        //从数据库查询用户信息
        UserDTO user = userService.getUserByName(username);

        //可以在这里直接对用户名校验,或者调用 CredentialsMatcher 校验
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (!password.equals(user.getUserPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if ("1".equals(user.getStatus())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }

        //这里验证authenticationToken和simpleAuthenticationInfo的信息
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getUserPassword(), getName());
        return simpleAuthenticationInfo;
    }

    /**
     * @MethodName doGetAuthorizationInfo
     * @Description 权限配置类,授权，即角色或者权限验证。
     * 1 shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();来完成的。
     * 2 使用类：SimpleAuthorizationInfo 进行角色的添加和权限的添加
             * authorizationInfo.addRole(role.getRole());
             * authorizationInfo.addStringPermission(p.getPermission());
     * 3 也可以添加set集合：roles是从数据库查询的当前用户的角色，stringPermissions是从数据库查询的当前用户对应的权限
             *  authorizationInfo.setRoles(roles);
             *  authorizationInfo.setStringPermissions(stringPermissions);
     * 4 就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "perms[权限添加]");就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问
     * 5 如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "roles[100002]，perms[权限添加]");就说明访问/add这个链接必须要有 "权限添加" 这个权限和具有 "100002" 这个角色才可以访问
     *
     * @Param [principalCollection]
     * @Return AuthorizationInfo
     * @Author WangShiLin
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //获取用户
        UserDTO user = (UserDTO) SecurityUtils.getSubject().getPrincipal();

        //获取用户角色
        Set<RoleDTO> roles =this.roleService.findRolesByUserId(user.getId());

        //添加角色
        SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();
        for (RoleDTO role : roles) {
            authorizationInfo.addRole(role.getRoleName());
        }

        //获取用户权限
        Set<PermissionDTO> permissions =  this.permissionService.findPermissionsByRoleId(roles);
        //添加权限
        for (PermissionDTO permission:permissions) {
            authorizationInfo.addStringPermission(permission.getPermissionName());
        }


        return authorizationInfo;
    }


    /**
     * 重写方法,清除当前用户的的 授权缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
