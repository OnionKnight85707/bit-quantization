package com.binance.client.examples.exchange.trade;

import com.binance.client.examples.exchange.constants.PrivateConfig;
import com.binance.client.spot.SpotRequestClient;
import com.binance.client.spot.SpotRequestOptions;

/**
 * @author : wangwanlu
 * @since : 2020/4/23, Thu
 **/
public class AddIsolatedPositionMargin {

    static int INCREASE_MARGIN_TYPE = 1;
    static int DECREASE_MARGIN_TYPE = 2;

    public static void main(String[] args) {
        SpotRequestOptions options = new SpotRequestOptions();
        SpotRequestClient spotRequestClient = SpotRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        System.out.println(spotRequestClient.addIsolatedPositionMargin("BTCUSDT", INCREASE_MARGIN_TYPE, "100"));
    }
}
