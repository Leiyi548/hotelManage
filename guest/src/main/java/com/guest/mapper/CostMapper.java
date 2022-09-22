package com.guest.mapper;

import com.guest.pojo.po.Cost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guest.pojo.vo.MoneyTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
@Mapper
@Repository
public interface CostMapper extends BaseMapper<Cost> {
    List<Cost> getCostByRoomId(String roomId);
    boolean settleCostByRoomId(String roomId);
    boolean removeByRoomId(String roomId);
    int getNotCostNum(String roomId);
    boolean removeByCostTypeId(Integer id);
    List<MoneyTable> getFinancialStatement();
}
