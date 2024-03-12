package com.binance.client.examples.exchange.market;

import com.binance.client.examples.exchange.constants.PrivateConfig;
import com.binance.client.spot.SpotRequestClient;
import com.binance.client.spot.SpotRequestOptions;
import com.binance.client.spot.model.enums.PeriodType;

public class GetTakerLongShortRatio {
    public static void main(String[] args) {
        SpotRequestOptions options = new SpotRequestOptions();
        SpotRequestClient spotRequestClient = SpotRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        System.out.println(spotRequestClient.getTakerLongShortRatio("BTCUSDT", PeriodType._5m,null,null,10));


    }
}
