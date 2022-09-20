package com.guest.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guest.mapper.MenuMapper;
import com.guest.pojo.po.Menu;
import com.guest.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    //输出菜单信息
    @Override
    public List<Menu> getMenu() {
        return menuMapper.getMenu();
    }

}
