package com.mzwise.huobi.market.socket.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradePlateItem {
    private BigDecimal price;
    private BigDecimal amount;
}
