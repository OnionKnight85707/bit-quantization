package com.binance.client.examples.exchange.websocket;

import com.binance.client.examples.exchange.constants.PrivateConfig;
import com.binance.client.spot.SubscriptionClient;
import com.binance.client.spot.model.enums.CandlestickInterval;

public class SubscribeCandlestick {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
   
        client.subscribeCandlestickEvent("btcusdt", CandlestickInterval.ONE_MINUTE, ((event) -> {
            System.out.println(event);
//            client.unsubscribeAll();
        }), null);

    }

}
