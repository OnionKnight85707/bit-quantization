package com.binance.client.examples.swap.user;

import com.binance.client.future.SubscriptionClient;
import com.binance.client.examples.swap.constants.PrivateConfig;
import com.binance.client.future.FutureRequestClient;
import com.binance.client.future.FutureRequestOptions;

public class SubscribeUserData {

    public static void main(String[] args) {

        FutureRequestOptions options = new FutureRequestOptions();
        FutureRequestClient futureRequestClient = FutureRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        // Start user data stream
        String listenKey = futureRequestClient.startUserDataStream();
        System.out.println("listenKey: " + listenKey);

        // Keep user data stream
        futureRequestClient.keepUserDataStream(listenKey);

        // Close user data stream
        futureRequestClient.closeUserDataStream(listenKey);

        SubscriptionClient client = SubscriptionClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);

   
        client.subscribeUserDataEvent(listenKey, ((event) -> {
            System.out.println(event);
        }), null);

    }

}