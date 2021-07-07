package com.klein.shiro;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {


    /**
     *
     * https://www.baiyp.ren/SpringBoot%E6%95%B4%E5%90%88Shiro.html
     * */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     * @return
     */
    @Bean
    public CustomRealm myShiroRealm() {
        return new CustomRealm();
    }

    /**
     * 配置核心安全事务管理器
     * */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义realm.
        securityManager.setRealm(myShiroRealm());

        //配置记住我 参考博客：
        //securityManager.setRememberMeManager(rememberMeManager());

        //配置 redis缓存管理器 参考博客：
        //securityManager.setCacheManager(getEhCacheManager());

        //配置自定义session管理，使用redis 参考博客：
        //securityManager.setSessionManager(sessionManager());


        return securityManager;
    }


    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Web应用中,Shiro可控制的Web请求必须经过Shiro主过滤器的拦截
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //必须设置 SecurityManager,Shiro的核心安全接口
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //这里的/login是后台的接口名,非页面，如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //这里的/index是后台的接口名,非页面,登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");

        // 配置页面路由拦截规则，注意配置顺序 /**  放在最后
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //配置不登录可以访问的资源，anon 表示资源都可以匿名访问
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/", "anon");

        /*swagger2 页面不需要登录访问*/
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/configuration/**", "anon");
        filterChainDefinitionMap.put("/doc.html","anon");
        filterChainDefinitionMap.put("/swagger-ui.html**", "anon");

        filterChainDefinitionMap.put("/druid/**", "anon");
        //logout登出是shiro提供的过滤器
        filterChainDefinitionMap.put("/logout", "logout");
        //authc 表示需要认证才能进行访问，其他资源都需要认证
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //自定义拦截器限制并发人数,参考博客：
        //LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //限制同一帐号同时在线的个数
        //filtersMap.put("kickout", kickoutSessionControlFilter());
        //shiroFilterFactoryBean.setFilters(filtersMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro 注解模式
     * 可以在controller中的方法前加上注解
     * 如 @RequiresPermissions("userInfo:add")
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions)
     * @return
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }







}
