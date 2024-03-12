package com.binance.client.spot.model.market;

import com.binance.client.spot.constant.SpotBinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class OrderBookEntry {

    private BigDecimal price;

    private BigDecimal qty;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, SpotBinanceApiConstants.TO_STRING_BUILDER_STYLE).append("price", price)
                .append("qty", qty).toString();
    }
}
