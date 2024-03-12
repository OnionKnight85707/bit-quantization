package com.mzwise.huobi.market.socket.core;

import com.mzwise.huobi.market.socket.entity.*;

import java.util.List;
import java.util.Set;

public interface HuobiBaseHandler {
    /**
     * 获取所有交易对(必须实现)
     * @return
     */
    Set<String> symbols();

    /**
     * 获取现有k线最大值(订阅k线必须实现)
     * @param symbol
     * @param period
     * @return
     */
    long findMaxTimestamp(String symbol, PeriodEnum period);

    /**
     * 刷新最新thumb(订阅行情必须实现)
     * @param symbol
     * @param thumb
     */
    void refreshThumb(String symbol, Thumb thumb);

    /**
     * 刷新k线(实时产生的k线)(订阅k线必须实现)
     * @param symbol
     * @param kLine
     */
    void refreshKline(String symbol, String period, KLine kLine);

    /**
     * 补充k线，初始化或断线补充(订阅k线必须实现)
     * @param symbol
     * @param kLines
     */
    void supplyKline(String symbol, String period, List<KLine> kLines);

    /**
     * 刷新盘口信息(订阅盘口必须实现)
     * @param buyPlateItems
     * @param sellPlateItems
     */
    void refreshPlate(String symbol, List<TradePlateItem> buyPlateItems, List<TradePlateItem> sellPlateItems);


    /**
     * 刷新最新成交(订阅最新成交订单必须实现)
     * @param tradeArrayList
     */
    void refreshLastedTrade(String symbol, List<Trade> tradeArrayList);


}
