package com.lsj.pojo;

import java.util.List;

/**
 * @ClassName Menu
 * @Description 输入描述
 * @Author Lin
 * @Date 2019/6/5 0005 20:53
 **/
public class Menu {
    private Integer id;
    private String pattern;
    private List<Role> roles;


    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
