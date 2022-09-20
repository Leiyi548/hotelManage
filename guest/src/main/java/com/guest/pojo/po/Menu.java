package com.guest.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class Menu extends Model<Menu> {
    @TableId(value = "dish_id")
    int dish_id;//菜编号
    String dish_name;//菜名
    float dish_price;//菜价

}
