package com.binance.client.examples.swap.trade;

import com.binance.client.future.FutureRequestOptions;
import com.binance.client.future.FutureRequestClient;
import com.binance.client.examples.swap.constants.PrivateConfig;

/**
 * @author : wangwanlu
 * @since : 2020/4/7, Tue
 **/
public class CancelAllOpenOrders {

    public static void main(String[] args) {
        FutureRequestOptions options = new FutureRequestOptions();
        FutureRequestClient futureRequestClient = FutureRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        System.out.println(futureRequestClient.cancelAllOpenOrder("BTCUSDT"));
    }
}
