package com.binance.client.examples.swap.websocket;

import com.binance.client.future.SubscriptionClient;
import com.binance.client.examples.swap.constants.PrivateConfig;

public class SubscribeSymbolMiniTicker {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
   
        client.subscribeSymbolMiniTickerEvent("btcusdt", ((event) -> {
            System.out.println(event);
            client.unsubscribeAll();
        }), null);

    }

}
