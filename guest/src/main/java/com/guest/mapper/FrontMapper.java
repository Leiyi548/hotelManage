package com.guest.mapper;

import com.guest.pojo.po.Front;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
@Mapper
@Repository
public interface FrontMapper extends BaseMapper<Front> {

}
