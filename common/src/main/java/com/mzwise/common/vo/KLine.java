package com.mzwise.common.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KLine {
    public KLine() {

    }

    public KLine(String period) {
        this.period = period;
    }

    private BigDecimal open = BigDecimal.ZERO;
    private BigDecimal high = BigDecimal.ZERO;
    private BigDecimal low = BigDecimal.ZERO;
    private BigDecimal close = BigDecimal.ZERO;

    public BigDecimal getField(String field) {
        switch (field) {
            case "high":
                return this.high;
            case "low":
                return this.low;
            case "open":
                return this.open;
            default:
                return this.close;
        }
    }

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
