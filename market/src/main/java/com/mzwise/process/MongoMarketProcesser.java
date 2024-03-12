package com.mzwise.process;

import com.mzwise.huobi.market.socket.entity.*;
import com.mzwise.service.KlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoMarketProcesser implements MarketProcesser {

    @Autowired
    private KlineService klineService;

    @Override
    public void handleThumb(String symbol, Thumb thumb) {

    }

    @Override
    public void handlePlate(String symbol, TradePlate plate) {

    }

    @Override
    public void handleKLine(String symbol, KLine kLine) {
        klineService.saveKLine(symbol, kLine);
    }

    @Override
    public void handleTrade(String symbol, List<Trade> trades) {

    }
}
