package com.mzwise.process;

import com.mzwise.huobi.market.socket.entity.*;
import java.util.List;

public interface MarketProcesser {

    /**
     * 存储币种信息
     */
    void handleThumb(String symbol, Thumb thumb);

    /**
     * 处理深度信息
     */
    void handlePlate(String symbol, TradePlate plate);

    /**
     * 存储K线信息
     *
     * @param kLine
     */
    void handleKLine(String symbol, KLine kLine);

    /**
     * 存储交易
     */
    void handleTrade(String symbol, List<Trade> trades);
}
