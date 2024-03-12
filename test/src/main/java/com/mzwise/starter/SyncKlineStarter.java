package com.mzwise.starter;

import com.mzwise.sync.kline.MongoMarketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 启动并订阅指标分析
 */
@Slf4j
@Component
public class SyncKlineStarter implements ApplicationRunner {

    @Autowired
    private MongoMarketHandler mongoMarketHandler;

    @Override
    @Async
    public void run(ApplicationArguments args) {
//        log.info("=================== 开始kline导入 ===================");
//        MarketClient marketClient = MarketClient.create(new HuobiOptions());
//        String[] symbols = {
//                "DOGE/USDT", // 为了FTC
//                "BTC/USDT",
//                "AAVE/ETH",
//                "ADA/BTC",
//                "ADA/ETH",
//                "ADA/USDT",
//                "BAT/BTC",
//                "BCH/BTC",
//                "BCH/USDT",
//                "BOR/ETH",
//                "BSV/BTC",
//                "BSV/USDT",
//                "BTM/BTC",
//                "COMP/ETH",
//                "DASH/USDT",
//                "DCR/ETH",
//                "DGD/ETH",
//                "DOGE/BTC",
//                "DOT/USDT",
//                "ELA/BTC",
//                "EOS/BTC",
//                "EOS/USDT",
//                "ETC/USDT",
//                "ETH/BTC",
//                "ETH/USDT",
//                "HT/BTC",
//                "HT/ETH",
//                "HT/USDT",
//                "IOTA/USDT",
//                "LINK/BTC",
//                "LINK/ETH",
//                "LINK/USDT",
//                "LTC/USDT",
//                "MANA/BTC",
//                "OGN/BTC",
//                "PAX/USDT",
//                "QTUM/BTC",
//                "TRX/BTC",
//                "TRX/USDT",
//                "USDC/USDT",
//                "WBTC/ETH",
//                "WBTC/USDT",
//                "XMR/USDT",
//                "XRP/BTC",
//                "XRP/USDT",
//                "YFI/ETH",
//                "ZEC/USDT",
//                "ZEN/ETH",
//                "ZRX/BTC"
//        };
//
//        HashMap<String, CandlestickIntervalEnum> periods = new HashMap<String, CandlestickIntervalEnum>() {{
//            put("1min", CandlestickIntervalEnum.MIN1);
//            put("5min", CandlestickIntervalEnum.MIN5);
//            put("15min", CandlestickIntervalEnum.MIN15);
//            put("30min", CandlestickIntervalEnum.MIN30);
//            put("1hour", CandlestickIntervalEnum.MIN60);
//            put("1day", CandlestickIntervalEnum.DAY1);
//            put("1week", CandlestickIntervalEnum.WEEK1);
//            put("1mon", CandlestickIntervalEnum.MON1);
//        }};
//
//        for (String symbol : symbols) {
//            String toSymbol = symbol.replace("/", "").toLowerCase();
//            for (Map.Entry<String, CandlestickIntervalEnum> c : periods.entrySet()) {
//                String period = c.getKey();
//                CandlestickIntervalEnum interval = c.getValue();
//                try {
//                    List<Candlestick> list = marketClient.getCandlestick(CandlestickRequest.builder()
//                            .symbol(toSymbol)
//                            .interval(interval)
//                            .size(2000)
//                            .build());
//                    if (symbol.equals("DOGE/USDT")) {
//                        String symbol2 = "FTC/USDT";
//                        System.out.println("获取到kline, "+ symbol2 + "," + period + ", 2000根");
//                        for (Candlestick candlestick : list) {
//                            long from = 1618117200l; // 开始时间
//                            long to = System.currentTimeMillis();
//                            to = to / 1000;
//                            if (candlestick.getId()>to) {
//                                System.out.println("时间过滤");
//                                continue;
//                            }
//                            if (candlestick.getId()<from) {
//                                System.out.println("时间过滤");
//                                break;
//                            }
//                            BigDecimal bei = BigDecimal.valueOf(1.286173);
//                            System.out.println("倍数+"+bei);
//                            KLine kLine = new KLine().builder()
//                                    .period(period)
//                                    .closePrice(candlestick.getClose().multiply(bei).setScale(6, BigDecimal.ROUND_DOWN))
//                                    .highestPrice(candlestick.getHigh().multiply(bei).setScale(6, BigDecimal.ROUND_DOWN))
//                                    .lowestPrice(candlestick.getLow().multiply(bei).setScale(6, BigDecimal.ROUND_DOWN))
//                                    .openPrice(candlestick.getOpen().multiply(bei).setScale(6, BigDecimal.ROUND_DOWN))
//                                    .time(candlestick.getId() * 1000)
//                                    .volume(candlestick.getVol())
//                                    .count(candlestick.getCount().intValue())
//                                    .turnover(candlestick.getAmount()).build();
//                            mongoMarketHandler.handleKLine(symbol2, kLine);
//                        }
//                    } else {
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//        log.info("=================== kline 导入成功 ===================");
    }
}
