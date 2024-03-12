package com.mzwise.process;

import com.mzwise.huobi.market.socket.entity.*;
import com.mzwise.job.ThumbPushJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * netty 推送数据
 * @author wmf
 */
@Slf4j
@Service
public class NettyMarketProcesser implements MarketProcesser {
    @Autowired
    private ThumbPushJob thumbPushJob;

    @Override
    public void handleThumb(String symbol, Thumb thumb) {
        thumbPushJob.addThumb(symbol, thumb);
    }

    @Override
    public void handlePlate(String symbol, TradePlate plate) {
    }

    @Override
    public void handleKLine(String symbol, KLine kLine) {

    }

    @Override
    public void handleTrade(String symbol, List<Trade> trades) {

    }
}
