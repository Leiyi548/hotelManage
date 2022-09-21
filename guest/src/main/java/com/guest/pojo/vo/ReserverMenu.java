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
    Integer id;
    String reserver_name;//预订人姓名
    String reserver_tel;//预订人电话
    Integer dish_id;//菜品id
    int state;//结账状态  1结账  0未结账
}
