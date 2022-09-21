package com.guest.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserver extends Model<Reserver> {

    String reserverName;//预订人姓名
    String reserverTel;//预订人号码
    Integer eaterNum;//就餐人数
    Integer deskNum;//就餐餐桌号

}
