package com.guest.service.serviceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guest.mapper.ReserverMenuMapper;
import com.guest.pojo.po.Menu;
import com.guest.pojo.vo.ReserverMenu;
import com.guest.service.ReserverMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReserverMenuServiceImpl extends ServiceImpl<ReserverMenuMapper,ReserverMenu> implements ReserverMenuService {
    @Autowired
    ReserverMenuMapper reserverMenuMapper;

    //增加多条用户点单记录
    @Override
    public int addDishes(List<ReserverMenu> reserverMenu) {
        return reserverMenuMapper.addDishes(reserverMenu);
    }

    //根据手机号查找消费菜单
    @Override
    public List<Menu> searchByTelAndState(String reserver_tel) {
        return reserverMenuMapper.searchByTelAndState(reserver_tel);
    }

    //完成支付后 根据手机号 查询消费记录  将state=1  表示已支付
    @Override
    public int finishBill(String reserver_tel) {
        return reserverMenuMapper.finishBill(reserver_tel);
    }
}
