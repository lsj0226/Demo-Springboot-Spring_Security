package com.lsj.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

public class MyAccessDecisionManager implements AccessDecisionManager {


    /*自定义验证整体说明
    * 1.首先判断此次请求中需求的角色是否为ROLE_LOGIN(自己定义的)且当前是否已经登录
    *   验证通过，说明该请求不在菜单表menu中，且访问不需要权限(一般来说是错误的路径，此时会跳到错误页)，直接返回即可
    * 2.将当前用户登录的信息中的角色列表与此次请求的url匹配到可以访问该菜单资源的角色进行匹配
    *   匹配上了，说明该用户包含该角色权限，直接返回即可
    *   没有匹配上，说明该用户不包含该角色权限，抛出错误异常*/
    @Override
    public void decide(Authentication auth, //包含当前登录用户的信息
                       Object o, //FilterInvocation对象
    Collection<ConfigAttribute> ca //FilterInvocationSecurityMetadataSource中的getAttributes返回值，包含可以访问该次请求的用户角色
    ) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : ca) {
            if("ROLE_LOGIN".equals(configAttribute.getAttribute()) &&
                    auth instanceof UsernamePasswordAuthenticationToken) {//实例表明当前用户已登录
                return ;
            }
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals(configAttribute.getAttribute())){
                    return ;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
