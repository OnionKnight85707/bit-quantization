package com.mzwise.unifyexchange.spot;

import com.mzwise.unifyexchange.beans.Order;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.common.StreamListener;
import com.mzwise.unifyexchange.spot.stream.*;

import java.util.HashMap;
import java.util.Map;

public class SpotStreamServiceFactory {

    private static Map<Constants.EXCHANGE_NAME, StreamServiceSPISpot> services = new HashMap<>();

    public static synchronized StreamServiceSPISpot getInstance(Constants.EXCHANGE_NAME exchange) {
        if (services.containsKey(exchange)) {
            return services.get(exchange);
        } else {
            StreamServiceSPISpot spi = new StreamServiceSPISpot(exchange);
            services.put(exchange, spi);
            return spi;
        }
    }

    public final static class StreamServiceSPISpot implements SpotIStreamService {

        private SpotIStreamService streamService;

        public StreamServiceSPISpot(Constants.EXCHANGE_NAME exchange) {
            if (Constants.EXCHANGE_NAME.HUOBI.equals(exchange)) {
                streamService = new HuobiStreamServiceSpot();
            } else if (Constants.EXCHANGE_NAME.COINEX.equals(exchange)) {
                streamService = new CoinexStreamServiceSpot();
            } else if (Constants.EXCHANGE_NAME.OKEX.equals(exchange)) {
                streamService = new OkexStreamServiceSpot();
            } else if (Constants.EXCHANGE_NAME.BINANCE.equals(exchange)) {
                streamService = new BinanceStreamServiceSpot();
            }
        }


        @Override
        public void init() {
            streamService.init();
        }

        @Override
        public void subOrderUpdate(String id, String accessKey, String secretkey, String passphrase, StreamListener<Order.Response> streamListener) {
            streamService.subOrderUpdate(id, accessKey, secretkey, passphrase, streamListener);
        }
    }
}
