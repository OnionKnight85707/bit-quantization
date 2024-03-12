package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PlatformEnum implements IEnum<Integer> {

    // 火币
    HUOBI(0, "HUOBI"),

    // 币安
    BINANCE(1, "BINANCE"),

    // OKEX
    OKEX(2, "OKEX"),

    // Coinbase Pro
    COINBASE_PRO(3, "COINBASE_PRO"),

    // Bitfinex
    BITFINEX(4, "BITFINEX"),

    // 富途证券
    FUTU_SECURITIES(5, "FUTU_SECURITIES"),

    // 国泰君安
    GUOTAIJUNAN(6, "GUOTAIJUNAN"),

    // 海通期货
    HAITONG_FUTURES(7, "HAITONG_FUTURES");

    private final Integer value;
    private final String name;
}