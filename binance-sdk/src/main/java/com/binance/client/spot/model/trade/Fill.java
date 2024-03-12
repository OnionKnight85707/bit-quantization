package com.binance.client.spot.model.trade;

import java.math.BigDecimal;

public class Fill {
    // 交易的价格
    private BigDecimal price;
    // 交易的数量
    private BigDecimal qty;
    // 手续费金额
    private BigDecimal commission;
    // 手续费的币种
    private String commissionAsset;

    public Fill(BigDecimal price, BigDecimal qty, BigDecimal commission, String commissionAsset) {
        this.price = price;
        this.qty = qty;
        this.commission = commission;
        this.commissionAsset = commissionAsset;
    }

    @Override
    public String toString() {
        return "{\"Fill\":{"
                + "\"price\":"
                + price
                + ",\"qty\":"
                + qty
                + ",\"commission\":"
                + commission
                + ",\"commissionAsset\":\""
                + commissionAsset + '\"'
                + "}}";

    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getCommissionAsset() {
        return commissionAsset;
    }

    public void setCommissionAsset(String commissionAsset) {
        this.commissionAsset = commissionAsset;
    }
}
