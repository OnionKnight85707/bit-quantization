package com.binance.client.spot.model.trade;

import java.math.BigDecimal;
import java.util.List;

public class Order {

    private String clientOrderId;

    private BigDecimal executedQty;

    private BigDecimal cummulativeQuoteQty;

    private Long orderId;

    private BigDecimal origQty;

    private BigDecimal price;

    private String side;

    private String status;

//    private BigDecimal stopPrice;

    private String symbol;

    private String timeInForce;

    private String type;

    private List<Fill> fills;

//    private Long updateTime;

//    private String workingType;

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public BigDecimal getExecutedQty() {
        return executedQty;
    }

    public void setExecutedQty(BigDecimal executedQty) {
        this.executedQty = executedQty;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public BigDecimal getStopPrice() {
//        return stopPrice;
//    }
//
//    public void setStopPrice(BigDecimal stopPrice) {
//        this.stopPrice = stopPrice;
//    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(String timeInForce) {
        this.timeInForce = timeInForce;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getCummulativeQuoteQty() {
        return cummulativeQuoteQty;
    }

    public void setCummulativeQuoteQty(BigDecimal cummulativeQuoteQty) {
        this.cummulativeQuoteQty = cummulativeQuoteQty;
    }

    //    public Long getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Long updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    public String getWorkingType() {
//        return workingType;
//    }
//
//    public void setWorkingType(String workingType) {
//        this.workingType = workingType;
//    }


    public List<Fill> getFills() {
        return fills;
    }

    public void setFills(List<Fill> fills) {
        this.fills = fills;
    }

    @Override
    public String toString() {
        return "{\"Order\":{"
                + "\"clientOrderId\":\""
                + clientOrderId + '\"'
                + ",\"executedQty\":"
                + executedQty
                + ",\"cummulativeQuoteQty\":"
                + cummulativeQuoteQty
                + ",\"orderId\":"
                + orderId
                + ",\"origQty\":"
                + origQty
                + ",\"price\":"
                + price
                + ",\"side\":\""
                + side + '\"'
                + ",\"status\":\""
                + status + '\"'
                + ",\"symbol\":\""
                + symbol + '\"'
                + ",\"timeInForce\":\""
                + timeInForce + '\"'
                + ",\"type\":\""
                + type + '\"'
                + ",\"fills\":"
                + fills
                + "}}";

    }
}
