package com.guest.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guest.mapper.ReserverMapper;
import com.guest.pojo.po.Reserver;
import com.guest.service.ReserverService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReserverServiceImpl extends ServiceImpl<ReserverMapper,Reserver> implements ReserverService {
    @Autowired
    ReserverMapper reserverMapper;

    //增加预定信息
    @Override
    public int addReserver(Reserver reserver) {
        return reserverMapper.addReserver(reserver);
    }

    //通过预订者姓名查找预定信息  若无预订者信息 则查找全部
    @Override
    public List searchReserverByName(String reserver_name){
        return reserverMapper.searchReserverByName(reserver_name);
    }

    //通过预订者电话号码删除预定信息 连表删除  同时将其点菜记录删除
    @Override
    public int deleteReserverByTel(String reserver_tel){
        return reserverMapper.deleteReserverByTel(reserver_tel);
    }

    //通过预订者电话号码删除预定信息  只删除预定者信息
    @Override
    public  int deleteReserverByTel2(String reserver_tel){
        return reserverMapper.deleteReserverByTel2(reserver_tel);
    }



}
