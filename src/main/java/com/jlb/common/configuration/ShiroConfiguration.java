package com.jlb.common.configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.jlb.model.util.MyHashedCredentialsMatcher;
import com.jlb.model.util.MyType;
import com.jlb.model.Realm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {
    @Autowired
    ShiroDialect shiroDialect;

    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public MyHashedCredentialsMatcher hashedCredentialsMatcher() {
        MyHashedCredentialsMatcher credentialsMatcher = new MyHashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashAlgorithmName("sha-256");
        //加密次数
        credentialsMatcher.setHashIterations(MyType.ENCIPHER_CONST);
        credentialsMatcher.setStoredCredentialsHexEncoded(false);
        return credentialsMatcher;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager gerDefaultWebSecurityManager(@Qualifier("realm") Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(realm);
        return securityManager;
    }


    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         * 添加shiro的内置过滤器，可以实现权限相关的拦截器
         * 常用的过滤器：
         * anon:无需认证（登录）即可访问
         * authc：必须认证才能访问
         * user：如果使用rememberMe的功能即可直接访问
         * perms：必须授予资源权限才能访问
         * role:必须授予角色授权才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<String, String>();
//        filterMap.put("/common/*", "authc");
        //放行静态资源
        filterMap.put("/static/*", "anon");
        //放行页面
        filterMap.put("/common/toLogin", "anon");
        filterMap.put("/login/**", "anon");
        filterMap.put("/common/toError", "anon");
        filterMap.put("/common/toNoPermission", "anon");
//        filterMap.put("/common/toMember", "perms[1]");
        //页面未登录用户拦截
//        filterMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        //设置跳转的首页url
        shiroFilterFactoryBean.setLoginUrl("/login/toLogin");
        //设置未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/common/toNoPermission");
        return shiroFilterFactoryBean;
    }

    /**
     * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
     *
     * @return
     */
    @Bean(name="shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean(name = "realm")
    public Realm realm(@Qualifier("hashedCredentialsMatcher") MyHashedCredentialsMatcher hashedCredentialsMatcher) {
        Realm realm = new Realm();
        realm.setAuthorizationCachingEnabled(false);
        realm.setCredentialsMatcher(hashedCredentialsMatcher);
        return realm;
    }

}
