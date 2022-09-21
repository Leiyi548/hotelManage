package com.guest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.guest.pojo.po.Reserver;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReserverMapper  extends BaseMapper<Reserver> {
   int addReserver(Reserver reserver);
   Reserver getReserverByName(String ReserverName);
}
