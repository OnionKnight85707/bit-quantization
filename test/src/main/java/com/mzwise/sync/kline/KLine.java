package com.mzwise.sync.kline;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KLine {

    private BigDecimal openPrice = BigDecimal.ZERO;
    private BigDecimal highestPrice = BigDecimal.ZERO;
    private BigDecimal lowestPrice = BigDecimal.ZERO;
    private BigDecimal closePrice = BigDecimal.ZERO;
    private long time;
    private String period;

    /**
     * 成交笔数
     */
    private int count;
    /**
     * 成交量
     */
    private BigDecimal volume = BigDecimal.ZERO;
    /**
     * 成交额
     */
    private BigDecimal turnover = BigDecimal.ZERO;
}
