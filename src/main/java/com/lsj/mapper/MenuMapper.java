package com.lsj.mapper;

import com.lsj.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MenuMapper {
    List<Menu> getAllMenus();
}
