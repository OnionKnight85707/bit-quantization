package com.mzwise;

import com.huobi.client.MarketClient;
import com.huobi.client.req.market.CandlestickRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.enums.CandlestickIntervalEnum;
import com.huobi.model.market.Candlestick;
import com.mzwise.sync.kline.KLine;
import com.mzwise.sync.kline.MongoMarketHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class syncKline {

    @Autowired
    private MongoMarketHandler mongoMarketHandler;

    @Test
    public void go() {
        MarketClient marketClient = MarketClient.create(new HuobiOptions());
        String[] symbols = {
                "BTC/USDT",
                "AAVE/ETH",
                "ADA/BTC",
                "ADA/ETH",
                "ADA/USDT",
                "BAT/BTC",
                "BCH/BTC",
                "BCH/USDT",
                "BOR/ETH",
                "BSV/BTC",
                "BSV/USDT",
                "BTM/BTC",
                "COMP/ETH",
                "DASH/USDT",
                "DCR/ETH",
                "DGD/ETH",
                "DOGE/BTC",
                "DOT/USDT",
                "ELA/BTC",
                "EOS/BTC",
                "EOS/USDT",
                "ETC/USDT",
                "ETH/BTC",
                "ETH/USDT",
                "HT/BTC",
                "HT/ETH",
                "HT/USDT",
                "IOTA/USDT",
                "LINK/BTC",
                "LINK/ETH",
                "LINK/USDT",
                "LTC/USDT",
                "MANA/BTC",
                "OGN/BTC",
                "PAX/USDT",
                "QTUM/BTC",
                "TRX/BTC",
                "TRX/USDT",
                "USDC/USDT",
                "WBTC/ETH",
                "WBTC/USDT",
                "XMR/USDT",
                "XRP/BTC",
                "XRP/ETH",
                "XRP/USDT",
                "YFI/ETH",
                "ZEC/USDT",
                "ZEN/ETH",
                "ZRX/BTC"
        };

        HashMap<String, CandlestickIntervalEnum> periods = new HashMap<String, CandlestickIntervalEnum>() {{
            put("1min", CandlestickIntervalEnum.MIN1);
            put("5min", CandlestickIntervalEnum.MIN5);
            put("15min", CandlestickIntervalEnum.MIN15);
            put("30min", CandlestickIntervalEnum.MIN30);
            put("1hour", CandlestickIntervalEnum.MIN60);
            put("1day", CandlestickIntervalEnum.DAY1);
            put("1week", CandlestickIntervalEnum.WEEK1);
            put("1mon", CandlestickIntervalEnum.MON1);
        }};

//        String symbol = "btcusdt";
        for (String symbol : symbols) {
            String toSymbol = symbol.replace("/", "").toLowerCase();
            for (Map.Entry<String, CandlestickIntervalEnum> c : periods.entrySet()) {
                String period = c.getKey();
                CandlestickIntervalEnum interval = c.getValue();
                List<Candlestick> list = marketClient.getCandlestick(CandlestickRequest.builder()
                        .symbol(toSymbol)
                        .interval(interval)
                        .size(2000)
                        .build());
                System.out.println("获取到kline, "+ symbol + "," + period + ", 2000根");
                for (Candlestick candlestick : list) {
                    long from = 1519833600l; // 开始时间
                    long to = mongoMarketHandler.findMinTimestamp(symbol, period);
                    if (to==0) {
                        to = System.currentTimeMillis();
                    }
                    to = to / 1000;
                    if (candlestick.getId()>to) {
                        System.out.println("时间过滤");
                        continue;
                    }
                    if (candlestick.getId()<from) {
                        System.out.println("时间过滤");
                        break;
                    }
                    KLine kLine = new KLine().builder()
                            .period(period)
                            .closePrice(candlestick.getClose())
                            .highestPrice(candlestick.getHigh())
                            .lowestPrice(candlestick.getLow())
                            .openPrice(candlestick.getOpen())
                            .time(candlestick.getId() * 1000)
                            .volume(candlestick.getVol())
                            .count(candlestick.getCount().intValue())
                            .turnover(candlestick.getAmount()).build();
                    mongoMarketHandler.handleKLine(symbol, kLine);
                }
            }
        }

    }

}
