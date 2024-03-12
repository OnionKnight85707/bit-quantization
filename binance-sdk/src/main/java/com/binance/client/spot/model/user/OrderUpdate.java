package com.binance.client.spot.model.user;

import java.math.BigDecimal;

public class OrderUpdate {

    private String symbol;

    private String clientOrderId;

    private String side;

    private String type;

    private String timeInForce;

    private BigDecimal origQty;

    private BigDecimal price;

    private BigDecimal stopPrice;

    private String executionType;

    private String orderStatus;

    private Long orderId;

    private BigDecimal lastFilledQty;

    private BigDecimal cumulativeFilledQty;

    private BigDecimal lastFilledQuoteQty;

    private BigDecimal cumulativeFilledQuoteQty;

    private BigDecimal lastFilledPrice;

    private String commissionAsset;

    private BigDecimal commissionAmount;

    private Long orderTradeTime;

    private Long tradeID;

    private Boolean isMarkerSide;

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

    public Boolean getIsMarkerSide() {
        return isMarkerSide;
    }

    public void setIsMarkerSide(Boolean isMarkerSide) {
        this.isMarkerSide = isMarkerSide;
    }

    public BigDecimal getLastFilledQuoteQty() {
        return lastFilledQuoteQty;
    }

    public void setLastFilledQuoteQty(BigDecimal lastFilledQuoteQty) {
        this.lastFilledQuoteQty = lastFilledQuoteQty;
    }

    public BigDecimal getCumulativeFilledQuoteQty() {
        return cumulativeFilledQuoteQty;
    }

    public void setCumulativeFilledQuoteQty(BigDecimal cumulativeFilledQuoteQty) {
        this.cumulativeFilledQuoteQty = cumulativeFilledQuoteQty;
    }

    public Boolean getMarkerSide() {
        return isMarkerSide;
    }

    public void setMarkerSide(Boolean markerSide) {
        isMarkerSide = markerSide;
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
                ", stopPrice=" + stopPrice +
                ", executionType='" + executionType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderId=" + orderId +
                ", lastFilledQty=" + lastFilledQty +
                ", cumulativeFilledQty=" + cumulativeFilledQty +
                ", lastFilledQuoteQty=" + lastFilledQuoteQty +
                ", cumulativeFilledQuoteQty=" + cumulativeFilledQuoteQty +
                ", lastFilledPrice=" + lastFilledPrice +
                ", commissionAsset='" + commissionAsset + '\'' +
                ", commissionAmount=" + commissionAmount +
                ", orderTradeTime=" + orderTradeTime +
                ", tradeID=" + tradeID +
                ", isMarkerSide=" + isMarkerSide +
                '}';
    }
}
