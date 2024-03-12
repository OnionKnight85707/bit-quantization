package com.binance.client.examples.swap.websocket;

import com.binance.client.future.SubscriptionClient;
import com.binance.client.examples.swap.constants.PrivateConfig;
import com.binance.client.future.model.enums.CandlestickInterval;

public class SubscribeCandlestick {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
   
        client.subscribeCandlestickEvent("btcusdt", CandlestickInterval.ONE_MINUTE, ((event) -> {
            System.out.println(event);
//            client.unsubscribeAll();
        }), null);

    }

}
