package com.guest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.guest.pojo.po.Reserver;


public interface ReserverMapper  extends BaseMapper<Reserver> {
   int addReserver(Reserver reserver);

}
