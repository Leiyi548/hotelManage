package com.guest.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xinyuan
 * @Version 1.0
 * @date 2022/9/16  上午 9:01
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialStatement {
    //年份
    private Integer year;
    //月份
    private Integer month;
    //月金额
    private Double moneyTotal;

}
