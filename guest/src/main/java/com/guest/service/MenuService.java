package com.guest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guest.pojo.po.Cost;
import com.guest.pojo.po.Menu;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface MenuService extends IService<Menu> {
    List<Menu> getMenu();
}
