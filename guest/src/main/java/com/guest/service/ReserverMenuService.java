package com.guest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guest.pojo.po.Menu;
import com.guest.pojo.vo.ReserverMenu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReserverMenuService extends IService<ReserverMenu>{
    //增加一些列菜单信息，并将其所有state位 置为0  再进入此页面前统一将state=0；
    int addDishes(List<ReserverMenu> reserverMenu);

    //进行结账：根据state=0与点餐人电话号码 查找该顾客点了什么菜
    List<Menu> searchByTelAndState(String reserver_tel);

    //完成结账：根据点餐人电话号码，将其所有点的菜state置为1
    int finishBill(String reserver_tel);
}





