package com.mzwise.huobi.market.socket.entity;

public enum OrderDirectionEnum {
    NULL(0, ""),
    BUY(1, "做多"),
    SELL(2, "做空");

    OrderDirectionEnum(int number, String description) {
        this.code = number;
        this.description = description;
    }

    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
