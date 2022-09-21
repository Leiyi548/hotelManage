package com.guest.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserverMenu {
    @TableId(value = "ReserverMenu_Id")
    int id;
    String reserver_name;//预订人姓名
    String reserver_tel;//预订人电话
    int dish_id;//菜品id
    int num;//菜品数量
    double price;//菜品价格
}
