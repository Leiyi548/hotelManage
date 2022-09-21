package com.guest.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xinyuan
 * @Version 1.0
 * @date 2022/9/21  下午 9:52
 **/
// 预定人点过的菜
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserverOverMenu {
    private String dishName;
    private Integer num;
    private Double price;
}
