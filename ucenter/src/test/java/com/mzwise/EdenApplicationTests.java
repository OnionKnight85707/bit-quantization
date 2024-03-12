package com.mzwise;

import com.mzwise.common.component.RatePool;
import com.mzwise.modules.market.service.MarketCoinService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EdenApplicationTests {
    private Logger log = LoggerFactory.getLogger(EdenApplicationTests.class);

    @Autowired
    private RatePool ratePool;

//    @Autowired
//    private QuantCoinService quantCoinService;

    @Autowired
    private MarketCoinService marketCoinService;

//    @Test
//    public void syncCoin() {
//        List<QuantCoin> qcs = quantCoinService.list();
//        for (QuantCoin qc : qcs) {
//            MarketCoin marketCoin = new MarketCoin();
//            marketCoin.setBaseCoinScale(2);
//            marketCoin.setSymbolPair(qc.getSymbolPair());
//            marketCoin.setPlatform(qc.getPlatform());
//            marketCoinService.save(marketCoin);
//        }
//    }

    // todo 汇率处理
    @Test
    public void setRate() {
        ratePool.setRate("USD-CNY", BigDecimal.valueOf(6.5));
        ratePool.setRate("FIL", BigDecimal.valueOf(150));
        ratePool.setRate("BTE", BigDecimal.valueOf(0.1));
    }

    /**
     * 测试下单
     */
//    @Test
//    public void order() {
//        ExTradingServiceFactory.TradingServiceSPI spi = ExTradingServiceFactory.getInstance(PlatformToOtherExchangeAdapter.getExchangeName(PlatformEnum.BINANCE));
//        Order order = new Order();
//        order.setAccessKey("XubgEPLGYJSQc6WljPFOE2TDfLizV2A25DiPVhJYrpvN6mSXwRvGKtzCPyjTzQf2");
//        order.setSecretkey("yqZHeYU0Ncs2vRzAbcY8OhPyR7JqTvig2FXs6EqZVV8n3XCUYNuolkbzxzR7HcfO");
//        order.setSymbol("ZILUSDT");
//        order.setVolume("1");
//        order.setDirection(Constants.TRADING_DIRECTION.BUY);
//        order.setType(Constants.ORDER_TYPE.MARKET);
//        order.setRequestId("123");
//        order.setAccountId("1");
//        Order.Response response = spi.postOrder(order);
//        System.out.println(response);
//    }

    @Test
    public void getRate() {
        BigDecimal rate = ratePool.getRate("USDT", "BTE");
        System.out.println(rate);
    }

    @Autowired
    private MarketCoinService coinService;
}
