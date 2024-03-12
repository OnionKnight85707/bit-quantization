package com.mzwise.service;

import com.mzwise.engine.CoinMatchFactory;
import com.mzwise.huobi.market.socket.core.HuobiBaseHandler;
import com.mzwise.huobi.market.socket.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class HuobiHandler implements HuobiBaseHandler {

    @Autowired
    private CoinMatchFactory factory;

    @Autowired
    private KlineService klineService;

    @Override
    public Set<String> symbols() {
        if (factory.getMatchMap()==null || factory.getMatchMap().size()==0) {
            return null;
        }
        return factory.getMatchMap().keySet();
    }

    @Override
    public long findMaxTimestamp(String symbol, PeriodEnum period) {
        return klineService.findMaxTimestamp(symbol, period);
    }

    @Override
    public void refreshThumb(String symbol, Thumb thumb) {
        factory.getCoinMatch(symbol).refreshThumb(thumb);
    }

    @Override
    public void refreshKline(String symbol, String period, KLine kLine) {
        factory.getCoinMatch(symbol).refreshKline(kLine);
    }

    @Override
    public void supplyKline(String symbol, String period, List<KLine> kLines) {
        for (KLine kLine : kLines) {
            factory.getCoinMatch(symbol).refreshKline(kLine);
        }
    }

    @Override
    public void refreshPlate(String symbol, List<TradePlateItem> buyPlateItems, List<TradePlateItem> sellPlateItems) {
        factory.getCoinMatch(symbol).refreshPlate(buyPlateItems, sellPlateItems);
    }

    @Override
    public void refreshLastedTrade(String symbol, List<Trade> tradeArrayList) {
        factory.getCoinMatch(symbol).refreshLastedTrade(tradeArrayList);
    }
}
