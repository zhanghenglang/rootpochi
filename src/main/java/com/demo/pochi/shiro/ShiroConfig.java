package com.demo.pochi.shiro;


import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
@Configuration
public class ShiroConfig {

    @Autowired
    private SysUserRealm sysUserRealm;
    @Autowired
    private ShopUserRealm shopUserRealm;
    /**
     * 配置shiroFilterFactoryBean
     *
     * @param securityManager
     * @return
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        // 设置安全管理器
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 把自定义的过滤器放到shiro中
        Map<String, Filter> shiroFilters = shiroFilterFactoryBean.getFilters();
        shiroFilters.put("authc", new LoginFilter());
        // 配置需要认证或者需要放行的路径，注意，这里必须是LinkedHashMap
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/sysUser/login","anon");
        filterMap.put("/weChat/**","anon");
        filterMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置安全管理器
     *
     * @param sessionDaoConfig
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(SessionDaoConfig sessionDaoConfig) {
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(modularRealmAuthenticator());
        Collection<Realm> realms=new ArrayList<>(2);
        realms.add(shopUserRealm);
        realms.add(sysUserRealm);
        securityManager.setRealms(realms);
        // 自定义session管理，使用redis进行管理
        TokenWebSessionManager sessionManager = new TokenWebSessionManager();
        sessionManager.setSessionDAO(sessionDaoConfig);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }
    /**
     * 系统自带的Realm管理，主要针对多realm
     * 注入后多realms才会生效
     * */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        //自己重写的ModularRealmAuthenticator
        PochiAuthenticator modularRealmAuthenticator = new PochiAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }
    /**
     * 管理生命周期
     * 如果不注入可能会无法启动
     * 注意方法要是静态的
     *
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 使用redis存sessionId
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        template.setValueSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        return stringRedisTemplate;
    }

    /**
     * 加入注解的使用，不加入注解不生效
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
