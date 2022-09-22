package com.guest.mapper;

import com.guest.pojo.po.CostType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface CostTypeMapper extends BaseMapper<CostType> {
    List<CostType> getCostTypeByName(String name);

    List<CostType> getAllCostType();

    boolean removeByName(String name);
}
