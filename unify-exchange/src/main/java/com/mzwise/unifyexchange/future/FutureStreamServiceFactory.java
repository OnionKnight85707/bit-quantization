package com.mzwise.unifyexchange.future;

import com.mzwise.unifyexchange.beans.Order;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.common.StreamListener;
import com.mzwise.unifyexchange.future.stream.*;

import java.util.HashMap;
import java.util.Map;

public class FutureStreamServiceFactory {

    private static Map<Constants.EXCHANGE_NAME, StreamServiceSPIFuture> services = new HashMap<>();

    public static synchronized StreamServiceSPIFuture getInstance(Constants.EXCHANGE_NAME exchange) {
        if (services.containsKey(exchange)) {
            return services.get(exchange);
        } else {
            StreamServiceSPIFuture spi = new StreamServiceSPIFuture(exchange);
            services.put(exchange, spi);
            return spi;
        }
    }

    public final static class StreamServiceSPIFuture implements FutureIStreamService {

        private FutureIStreamService streamService;

        public StreamServiceSPIFuture(Constants.EXCHANGE_NAME exchange) {
            if (Constants.EXCHANGE_NAME.HUOBI.equals(exchange)) {
                streamService = new HuobiStreamServiceFuture();
            } else if (Constants.EXCHANGE_NAME.COINEX.equals(exchange)) {
                streamService = new CoinexStreamServiceFuture();
            } else if (Constants.EXCHANGE_NAME.OKEX.equals(exchange)) {
                streamService = new OkexStreamServiceFuture();
            } else if (Constants.EXCHANGE_NAME.BINANCE.equals(exchange)) {
                streamService = new BinanceStreamServiceFuture();
            }
        }


        @Override
        public void init() {
            streamService.init();
        }

        @Override
        public void subOrderUpdate(Integer apiAccessId,String id, String accessKey, String secretkey, String passphrase, StreamListener<Order.Response> streamListener) {
            streamService.subOrderUpdate(apiAccessId,id, accessKey, secretkey, passphrase, streamListener);
        }
    }
}
