package com.binance.client.examples.exchange.websocket;

import com.binance.client.examples.exchange.constants.PrivateConfig;
import com.binance.client.spot.SubscriptionClient;

public class SubscribeAllTicker {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
   
        client.subscribeAllTickerEvent(((event) -> {
            System.out.println(event);
            client.unsubscribeAll();
        }), null);

    }

}
