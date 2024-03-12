package com.binance.client.future.model.user;

import com.binance.client.future.constant.FutureBinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class BalanceUpdate {

    private String asset;

    private BigDecimal walletBalance;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, FutureBinanceApiConstants.TO_STRING_BUILDER_STYLE).append("asset", asset)
                .append("walletBalance", walletBalance).toString();
    }
}
