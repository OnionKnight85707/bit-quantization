package com.binance.client.future.model.user;

import com.binance.client.future.constant.FutureBinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class OrderUpdate {

    private String symbol;

    private String clientOrderId;

    private String side;

    private String type;

    private String timeInForce;

    private BigDecimal origQty;

    private BigDecimal price;

    private BigDecimal avgPrice;

    private BigDecimal stopPrice;

    private String executionType;

    private String orderStatus;

    private Long orderId;

    private BigDecimal lastFilledQty;

    private BigDecimal cumulativeFilledQty;

    private BigDecimal lastFilledPrice;

    private String commissionAsset;

    private BigDecimal commissionAmount;

    private Long orderTradeTime;

    private Long tradeID;

    private BigDecimal bidsNotional;

    private BigDecimal asksNotional;

    private Boolean isMarkerSide;

    private Boolean isReduceOnly;

    private String workingType;

    private BigDecimal profit;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(String timeInForce) {
        this.timeInForce = timeInForce;
    }

    public BigDecimal getOrigQty() {
        return origQty;
    }

    public void setOrigQty(BigDecimal origQty) {
        this.origQty = origQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(BigDecimal stopPrice) {
        this.stopPrice = stopPrice;
    }

    public String getExecutionType() {
        return executionType;
    }

    public void setExecutionType(String executionType) {
        this.executionType = executionType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getLastFilledQty() {
        return lastFilledQty;
    }

    public void setLastFilledQty(BigDecimal lastFilledQty) {
        this.lastFilledQty = lastFilledQty;
    }

    public BigDecimal getCumulativeFilledQty() {
        return cumulativeFilledQty;
    }

    public void setCumulativeFilledQty(BigDecimal cumulativeFilledQty) {
        this.cumulativeFilledQty = cumulativeFilledQty;
    }

    public BigDecimal getLastFilledPrice() {
        return lastFilledPrice;
    }

    public void setLastFilledPrice(BigDecimal lastFilledPrice) {
        this.lastFilledPrice = lastFilledPrice;
    }

    public String getCommissionAsset() {
        return commissionAsset;
    }

    public void setCommissionAsset(String commissionAsset) {
        this.commissionAsset = commissionAsset;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public Long getOrderTradeTime() {
        return orderTradeTime;
    }

    public void setOrderTradeTime(Long orderTradeTime) {
        this.orderTradeTime = orderTradeTime;
    }

    public Long getTradeID() {
        return tradeID;
    }

    public void setTradeID(Long tradeID) {
        this.tradeID = tradeID;
    }

    public BigDecimal getBidsNotional() {
        return bidsNotional;
    }

    public void setBidsNotional(BigDecimal bidsNotional) {
        this.bidsNotional = bidsNotional;
    }

    public BigDecimal getAsksNotional() {
        return asksNotional;
    }

    public void setAsksNotional(BigDecimal asksNotional) {
        this.asksNotional = asksNotional;
    }

    public Boolean getIsMarkerSide() {
        return isMarkerSide;
    }

    public void setIsMarkerSide(Boolean isMarkerSide) {
        this.isMarkerSide = isMarkerSide;
    }

    public Boolean getIsReduceOnly() {
        return isReduceOnly;
    }

    public void setIsReduceOnly(Boolean isReduceOnly) {
        this.isReduceOnly = isReduceOnly;
    }

    public String getWorkingType() {
        return workingType;
    }

    public void setWorkingType(String workingType) {
        this.workingType = workingType;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "OrderUpdate{" +
                "symbol='" + symbol + '\'' +
                ", clientOrderId='" + clientOrderId + '\'' +
                ", side='" + side + '\'' +
                ", type='" + type + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", origQty=" + origQty +
                ", price=" + price +
                ", avgPrice=" + avgPrice +
                ", stopPrice=" + stopPrice +
                ", executionType='" + executionType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderId=" + orderId +
                ", lastFilledQty=" + lastFilledQty +
                ", cumulativeFilledQty=" + cumulativeFilledQty +
                ", lastFilledPrice=" + lastFilledPrice +
                ", commissionAsset='" + commissionAsset + '\'' +
                ", commissionAmount=" + commissionAmount +
                ", orderTradeTime=" + orderTradeTime +
                ", tradeID=" + tradeID +
                ", bidsNotional=" + bidsNotional +
                ", asksNotional=" + asksNotional +
                ", isMarkerSide=" + isMarkerSide +
                ", isReduceOnly=" + isReduceOnly +
                ", workingType='" + workingType + '\'' +
                ", profit=" + profit +
                '}';
    }
}
