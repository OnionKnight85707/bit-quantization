package com.binance.client.spot.model.market;

import com.binance.client.spot.constant.SpotBinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class OrderBook {

    private Long lastUpdateId;

    private List<OrderBookEntry> bids;

    private List<OrderBookEntry> asks;

    public Long getLastUpdateId() {
        return lastUpdateId;
    }

    public void setLastUpdateId(Long lastUpdateId) {
        this.lastUpdateId = lastUpdateId;
    }

    public List<OrderBookEntry> getBids() {
        return bids;
    }

    public void setBids(List<OrderBookEntry> bids) {
        this.bids = bids;
    }

    public List<OrderBookEntry> getAsks() {
        return asks;
    }

    public void setAsks(List<OrderBookEntry> asks) {
        this.asks = asks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SpotBinanceApiConstants.TO_STRING_BUILDER_STYLE)
                .append("lastUpdateId", lastUpdateId).append("bids", bids).append("asks", asks).toString();
    }
}
