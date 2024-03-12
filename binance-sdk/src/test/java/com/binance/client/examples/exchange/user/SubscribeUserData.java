package com.binance.client.examples.exchange.user;

import com.binance.client.spot.SpotRequestClient;
import com.binance.client.spot.SpotRequestOptions;
import com.binance.client.spot.SubscriptionClient;

public class SubscribeUserData {

    public static void main(String[] args) {

        SpotRequestOptions options = new SpotRequestOptions();
        SpotRequestClient spotRequestClient1 = SpotRequestClient.create("oUYX9hEV6d8fenjalCdIDqBgylzuRjBp5qQ6Idm57zCaTkTf2gsSgBUf65nJnCG7", "",
                options);

        // Start user data stream
        String listenKey = spotRequestClient1.startUserDataStream();
        System.out.println("客户 listenKey: " + listenKey);

//        ExRequestClient exRequestClient2 = ExRequestClient.create("um4Tui0LVv1R9osqSDYnZcaO84TvlddzidzwHUoIWxEww6rcjqsC7H2lS0JjutXx", "",
//                options);
//
//        // Start user data stream
//        String listenKey2 = exRequestClient2.startUserDataStream();
//        System.out.println("王明峰 listenKey: " + listenKey2);

        // Keep user data stream
//        exRequestClient.keepUserDataStream(listenKey);
//
//        // Close user data stream
//        exRequestClient.closeUserDataStream(listenKey);

        /**
         * 通用所有用户的客户端
         */
        SubscriptionClient client = SubscriptionClient.create();


        client.subscribeUserDataEvent(listenKey, ((event) -> {
            System.out.println("客户账号收到消息推送");
            System.out.println(event);
        }), null);

//        client.subscribeUserDataEvent(listenKey2, ((event) -> {
//            System.out.println("王明峰账号收到消息推送");
//            System.out.println(event);
//        }), null);
    }

}