package com.binance.client.future.model.trade;

import com.binance.client.future.constant.FutureBinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class Asset {

    private String asset;

    private BigDecimal walletBalance;

    private BigDecimal availableBalance;

    private BigDecimal initialMargin;

    private BigDecimal maintMargin;

    private BigDecimal marginBalance;

    private BigDecimal maxWithdrawAmount;

    private BigDecimal openOrderInitialMargin;

    private BigDecimal positionInitialMargin;

    private BigDecimal unrealizedProfit;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public BigDecimal getInitialMargin() {
        return initialMargin;
    }

    public void setInitialMargin(BigDecimal initialMargin) {
        this.initialMargin = initialMargin;
    }

    public BigDecimal getMaintMargin() {
        return maintMargin;
    }

    public void setMaintMargin(BigDecimal maintMargin) {
        this.maintMargin = maintMargin;
    }

    public BigDecimal getMarginBalance() {
        return marginBalance;
    }

    public void setMarginBalance(BigDecimal marginBalance) {
        this.marginBalance = marginBalance;
    }

    public BigDecimal getMaxWithdrawAmount() {
        return maxWithdrawAmount;
    }

    public void setMaxWithdrawAmount(BigDecimal maxWithdrawAmount) {
        this.maxWithdrawAmount = maxWithdrawAmount;
    }

    public BigDecimal getOpenOrderInitialMargin() {
        return openOrderInitialMargin;
    }

    public void setOpenOrderInitialMargin(BigDecimal openOrderInitialMargin) {
        this.openOrderInitialMargin = openOrderInitialMargin;
    }

    public BigDecimal getPositionInitialMargin() {
        return positionInitialMargin;
    }

    public void setPositionInitialMargin(BigDecimal positionInitialMargin) {
        this.positionInitialMargin = positionInitialMargin;
    }

    public BigDecimal getUnrealizedProfit() {
        return unrealizedProfit;
    }

    public void setUnrealizedProfit(BigDecimal unrealizedProfit) {
        this.unrealizedProfit = unrealizedProfit;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "asset='" + asset + '\'' +
                ", walletBalance=" + walletBalance +
                ", availableBalance=" + availableBalance +
                ", initialMargin=" + initialMargin +
                ", maintMargin=" + maintMargin +
                ", marginBalance=" + marginBalance +
                ", maxWithdrawAmount=" + maxWithdrawAmount +
                ", openOrderInitialMargin=" + openOrderInitialMargin +
                ", positionInitialMargin=" + positionInitialMargin +
                ", unrealizedProfit=" + unrealizedProfit +
                '}';
    }
}
