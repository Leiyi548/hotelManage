package com.guest.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xinyuan
 * @Version 1.0
 * @date 2022/9/15  下午 4:00
 * 财务报表
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTable {
    private String name;
    private Double money;
    private Integer num;
    private Integer state;
    private String roomId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

}
