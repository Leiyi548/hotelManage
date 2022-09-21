package com.guest.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xinyuan
 * @Version 1.0
 * @date 2022/9/21  上午 10:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDish {

    String reserver_name;//预订人姓名
    String reserver_tel;//预订人电话
    List<String> dish_id;//菜品id数组
}
