package com.guest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guest.pojo.po.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getMenu();
}
