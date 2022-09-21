package com.guest.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guest.mapper.ReserverMapper;
import com.guest.pojo.po.Reserver;
import com.guest.service.ReserverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReserverServiceImpl extends ServiceImpl<ReserverMapper,Reserver> implements ReserverService {
    @Autowired
    ReserverMapper reserverMapper;
    //增加预定信息
    @Override
    public int addReserver(Reserver reserver) {
        return reserverMapper.addReserver(reserver);
    }

    @Override
    public Reserver getReserverByName(String ReserverName) {
        return reserverMapper.getReserverByName(ReserverName);
    }


}
