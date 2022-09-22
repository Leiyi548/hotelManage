package com.guest.service;

import com.guest.pojo.po.Cost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guest.pojo.vo.MoneyTable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
@Service
public interface CostService extends IService<Cost> {

    List<Cost> getCostByRoomId(String roomId);

    boolean settleCostByRoomId(String roomId);

    boolean removeByRoomId(String roomId);

    int getNotCostNum(String roomId);

    boolean removeByCostTypeId(Integer id);

    List<MoneyTable> getFinancialStatement();
}
