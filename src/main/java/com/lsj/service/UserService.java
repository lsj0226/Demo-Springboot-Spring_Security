package com.lsj.service;

import com.lsj.mapper.UserMapper;
import com.lsj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByName(username);
        if(user == null){
            throw new UsernameNotFoundException("账户或密码错误！");
        }
        user.setRoles(userMapper.getRolesByUid(user.getId()));
        return user;
    }
}
