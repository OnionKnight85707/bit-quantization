package com.binance.client.examples.swap.trade;

import com.binance.client.future.FutureRequestOptions;
import com.binance.client.future.FutureRequestClient;

import com.binance.client.examples.swap.constants.PrivateConfig;

public class PostOrder {
    public static void main(String[] args) {
        FutureRequestOptions options = new FutureRequestOptions();
        FutureRequestClient futureRequestClient = FutureRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
//        System.out.println(syncRequestClient.postOrder("BTCUSDT", OrderSide.SELL, PositionSide.BOTH, OrderType.LIMIT, TimeInForce.GTC,
//                "1", "1", null, null, null, null));

        // place dual position side order.
        // Switch between dual or both position side, call: com.binance.client.examples.swap.trade.ChangePositionSide
//        System.out.println(spRequestClient.postOrder("BTCUSDT", OrderSide.SELL, OrderType.LIMIT, TimeInForce.GTC,
//                "1", "9000", null, null, null, null, NewOrderRespType.RESULT));
    }
}