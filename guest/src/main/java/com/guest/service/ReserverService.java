package com.guest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guest.pojo.po.Reserver;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;


@Service
public interface ReserverService extends IService<Reserver> {
   int addReserver(Reserver reserver);
   Reserver getReserverByName(String name);
   int updateReserverState( String ReserverName, String ReserverTel);
}
