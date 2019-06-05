package com.lsj.config;

import com.lsj.mapper.MenuMapper;
import com.lsj.pojo.Menu;
import com.lsj.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher matcher = new AntPathMatcher();
    @Autowired
    MenuMapper menuMapper;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl(); //获取此次请求的url
        List<Menu> allMenus = menuMapper.getAllMenus(); //获取当前系统中所有的菜单信息,里面包含对应的角色关系
        for (Menu menu : allMenus) {
            if(matcher.match(menu.getPattern(), requestUrl)){ //判断当前具体的请求路径是否为对应菜单的请求路径
                List<Role> roles = menu.getRoles(); //获取可以访问该菜单路径的角色列表
                //创建角色列表数组，返回
                String[] roleArr = new String[roles.size()];
                for (int i = 0; i < roleArr.length; i++) {
                    roleArr[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(roleArr);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN"); //如果没有对应的菜单路径，就假设该请求需要登录后才能访问
    }

    /*用来返回所有定义后的资源权限，Spring Security会在启动时进行校验相关配置是否正确
    * 如果不需要校验，返回null即可*/
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    //返回类对象是否支持校验
    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
