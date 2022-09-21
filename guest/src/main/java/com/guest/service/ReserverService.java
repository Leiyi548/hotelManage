package com.guest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guest.pojo.po.Reserver;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ReserverService extends IService<Reserver> {
   int addReserver(Reserver reserver);
   List searchReserverByName(String reserver_name);
   int deleteReserverByTel(String reserver_tel);
   int deleteReserverByTel2(String reserver_tel);

}
