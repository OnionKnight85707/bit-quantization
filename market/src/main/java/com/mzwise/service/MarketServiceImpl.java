package com.mzwise.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.mzwise.common.api.CommonResult;
import com.mzwise.engine.CoinMatch;
import com.mzwise.engine.CoinMatchFactory;
import com.mzwise.huobi.market.socket.core.HuobiBaseHandler;
import com.mzwise.huobi.market.socket.core.HuobiMarket;
import com.mzwise.huobi.market.socket.core.WebSocketHuobiOptions;
import com.mzwise.huobi.market.socket.entity.*;
import com.mzwise.process.MongoMarketProcesser;
import com.mzwise.process.NettyMarketProcesser;
import com.mzwise.quant.rpc.Coin;
import com.mzwise.quant.rpc.service.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = MarketService.class)
@Component
@Slf4j
public class MarketServiceImpl implements MarketService {

    @Autowired
    private HuobiBaseHandler huobiHandler;

    @Autowired
    MongoMarketProcesser mongoMarketProcesser;

    @Autowired
    NettyMarketProcesser nettyMarketProcesser;

    @Autowired
    private CoinMatchFactory factory;

    @Override
    public CommonResult sub(List<Coin> coins) {
        log.info("=================== 火币行情订阅 ===================");
        if (factory.getMatchMap().size()!=0) {
            return CommonResult.failed();
        }
        for (Coin coin : coins) {
            // 简化处理。只订阅火币，所以要去重
            if (!factory.containsCoinMatch(coin.getSymbolPair())) {
                CoinMatch match = new CoinMatch(coin.getSymbolPair(), coin.getMarketQuoteScale());
                match.addProcess(mongoMarketProcesser);
                match.addProcess(nettyMarketProcesser);
                match.run();
                factory.addCoinMatch(coin.getSymbolPair(), match);
            }
        }

        log.info("========== 交易对数量：{}", factory.getMatchMap().size());
        HuobiMarket w = new HuobiMarket(huobiHandler, new WebSocketHuobiOptions(TopicEnum.KLINE, TopicEnum.THUMB));
        w.run();
        return CommonResult.success();
    }

    /**
     * 行情数据返回，根据初始化的币种列表，返回包含当前行情的币种列表
     * @return
     */
    @Override
    public List<com.mzwise.quant.rpc.Thumb> getThumb() {
        ArrayList<com.mzwise.quant.rpc.Thumb> thumbs = new ArrayList<>();
        Map<String, CoinMatch> matchMap = factory.getMatchMap();
        for (Map.Entry<String, CoinMatch> entry : matchMap.entrySet()) {
            CoinMatch coinMatch = entry.getValue();
            Thumb thumb = coinMatch.getThumb();
            com.mzwise.quant.rpc.Thumb th = new com.mzwise.quant.rpc.Thumb();
            BeanUtils.copyProperties(thumb, th);
            thumbs.add(th);
        }
        return thumbs;
    }

    /**
     * 行情数据返回，根据初始化的币种列表，返回包含当前行情的币种最新价格
     * @return
     */
    @Override
    public Map<String, BigDecimal> getPrices() {
        Map<String, BigDecimal> thumbs = new HashMap<>();
        Map<String, CoinMatch> matchMap = factory.getMatchMap();
        for (Map.Entry<String, CoinMatch> entry : matchMap.entrySet()) {
            CoinMatch coinMatch = entry.getValue();
            Thumb thumb = coinMatch.getThumb();
            thumbs.put(thumb.getSymbol(), thumb.getClose());
        }
        return thumbs;
    }
}
