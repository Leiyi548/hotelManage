package com.guest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.guest.pojo.po.Reserver;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReserverMapper  extends BaseMapper<Reserver> {
   int addReserver(Reserver reserver);
   List searchReserverByName(String reserver_name);
   int deleteReserverByTel(String reserver_tel);
   int deleteReserverByTel2(String reserver_tel);

}
