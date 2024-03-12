package com.binance.client.spot.model.trade;

import java.util.List;

public class AccountInformation {

    private Boolean canDeposit;

    private Boolean canTrade;

    private Boolean canWithdraw;

    private List<Asset> assets;

    public Boolean getCanDeposit() {
        return canDeposit;
    }

    public void setCanDeposit(Boolean canDeposit) {
        this.canDeposit = canDeposit;
    }

    public Boolean getCanTrade() {
        return canTrade;
    }

    public void setCanTrade(Boolean canTrade) {
        this.canTrade = canTrade;
    }

    public Boolean getCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(Boolean canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    @Override
    public String toString() {
        return "AccountInformation{" +
                "canDeposit=" + canDeposit +
                ", canTrade=" + canTrade +
                ", canWithdraw=" + canWithdraw +
                ", assets=" + assets +
                '}';
    }
}
