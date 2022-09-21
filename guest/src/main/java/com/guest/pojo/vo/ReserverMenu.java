package com.guest.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserverMenu {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String reserverName;//预订人姓名
    private String reserverTel;//预订人电话
    private String dishName;//菜品姓名
    private Integer num;
    private Double price;
}
