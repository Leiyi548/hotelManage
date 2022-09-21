package com.guest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.guest.pojo.po.Reserver;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReserverMapper  extends BaseMapper<Reserver> {
   int addReserver(Reserver reserver);
   Reserver getReserverByName(String ReserverName);

   @Update("update reserver set state = 1  " +
           "where reserver_name = #{ReserverName} and reserver_tel = #{ReserverTel};")
   int updateReserverState(@Param("ReserverName") String ReserverName,
                           @Param("ReserverTel") String ReserverTel);
}
