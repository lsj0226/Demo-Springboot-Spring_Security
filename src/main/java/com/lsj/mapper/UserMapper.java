package com.lsj.mapper;

import com.lsj.pojo.Role;
import com.lsj.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Description 输入描述
 * @Author Lin
 * @Date 2019/6/5 0005 19:05
 **/
@Mapper
public interface UserMapper {
    User loadUserByName(String username);
    List<Role> getRolesByUid(Integer id);
}
