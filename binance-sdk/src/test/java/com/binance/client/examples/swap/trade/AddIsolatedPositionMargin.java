package com.binance.client.examples.swap.trade;

import com.binance.client.future.FutureRequestOptions;
import com.binance.client.future.FutureRequestClient;
import com.binance.client.examples.swap.constants.PrivateConfig;

/**
 * @author : wangwanlu
 * @since : 2020/4/23, Thu
 **/
public class AddIsolatedPositionMargin {

    static int INCREASE_MARGIN_TYPE = 1;
    static int DECREASE_MARGIN_TYPE = 2;

    public static void main(String[] args) {
        FutureRequestOptions options = new FutureRequestOptions();
        FutureRequestClient futureRequestClient = FutureRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

//        System.out.println(spRequestClient.addIsolatedPositionMargin("BTCUSDT", INCREASE_MARGIN_TYPE, "100"));
    }
}
