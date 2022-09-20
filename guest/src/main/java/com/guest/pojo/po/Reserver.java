package com.guest.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserver extends Model<Reserver> {

    String reserver_name;//预订人姓名
    @TableId(value = "reserver_tel")
    String reserver_tel;//预订人号码
    Integer eater_num;//就餐人数
    Integer desk_num;//就餐餐桌号

}
