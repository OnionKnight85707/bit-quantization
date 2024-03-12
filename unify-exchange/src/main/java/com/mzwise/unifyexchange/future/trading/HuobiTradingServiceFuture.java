package com.mzwise.unifyexchange.future.trading;

import com.binance.client.future.model.trade.MyTrade;
import com.binance.client.future.model.trade.PositionRisk;
import com.mzwise.unifyexchange.beans.*;
import com.mzwise.unifyexchange.common.Constants;

import java.util.List;

public final class HuobiTradingServiceFuture implements FutureITradingService {

    @Override
    public AccountInfo.Response getAccountInfo(AccountInfo accountInfo) {
        return new AccountInfo.Response().builder()
                .status(Constants.RESPONSE_STATUS.OK)
                .isNormal(false)
                .build();
    }

    @Override
    public SymbolInfo.Response symbolInfo(SymbolInfo symbolInfo) {
        return null;
    }

    @Override
    public Risk.Response fixedRisk(Risk risk) {
        return new Risk.Response().builder()
                .status(Constants.RESPONSE_STATUS.OK)
                .build();
    }

    @Override
    public List<MyTrade> getHistoryTrade(HistoryTradeReq req) {
        return null;
    }

    @Override
    public Risk.Response getRisk(Risk risk) {
        return null;
    }

    @Override
    public List<PositionRisk> getNotNullRisks(Risk risk) {
        return null;
    }

    @Override
    public Lever.Response changeLever(Lever lever) {
        return null;
    }

    @Override
    public Margin.Response changeMarginType(Margin type) {
        return null;
    }

    @Override
    public Order.Response postUsdtOrder(Order order) {
        return null;
    }

    @Override
    public Order.Response detail(Order order) {
        return null;
    }

    @Override
    public Cancel.Response cancel(Cancel cancel) {
        return null;
    }

    @Override
    public List<Risk.Response> getAllRisk(Risk risk) {
        return null;
    }

    @Override
    public UnifyOrder getExchangeOrder(HistoryOrderReq req) {
        return null;
    }
}
