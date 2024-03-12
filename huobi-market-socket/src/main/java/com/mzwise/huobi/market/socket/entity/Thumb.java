package com.mzwise.huobi.market.socket.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Thumb implements Serializable {
    private String symbol;
    private BigDecimal open = BigDecimal.ZERO;
    private BigDecimal high = BigDecimal.ZERO;
    private BigDecimal low = BigDecimal.ZERO;
    private BigDecimal close = BigDecimal.ZERO;
    private BigDecimal chg = BigDecimal.ZERO.setScale(2);
    private BigDecimal change = BigDecimal.ZERO.setScale(2);
    private BigDecimal volume = BigDecimal.ZERO.setScale(2);
    private BigDecimal turnover = BigDecimal.ZERO;
    //昨日收盘价
    private BigDecimal lastDayClose = BigDecimal.ZERO;
    //交易币对usd汇率
    private BigDecimal usdRate;
    //基币对usd的汇率
    private BigDecimal baseUsdRate;
    //交易區
    private int zone;

    @Override
    public String toString() {
        return "{\"Thumb\":{"
                + "\"symbol\":\""
                + symbol + '\"'
                + ",\"open\":"
                + open
                + ",\"high\":"
                + high
                + ",\"low\":"
                + low
                + ",\"close\":"
                + close
                + ",\"chg\":"
                + chg
                + ",\"change\":"
                + change
                + ",\"volume\":"
                + volume
                + ",\"turnover\":"
                + turnover
                + ",\"lastDayClose\":"
                + lastDayClose
                + ",\"usdRate\":"
                + usdRate
                + ",\"baseUsdRate\":"
                + baseUsdRate
                + ",\"zone\":"
                + zone
                + "}}";

    }
}
