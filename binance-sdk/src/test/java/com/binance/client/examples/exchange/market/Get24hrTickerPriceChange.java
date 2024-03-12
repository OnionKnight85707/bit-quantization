package com.binance.client.examples.exchange.market;

import com.binance.client.examples.exchange.constants.PrivateConfig;
import com.binance.client.spot.SpotRequestClient;
import com.binance.client.spot.SpotRequestOptions;

public class Get24hrTickerPriceChange {
    public static void main(String[] args) {
        SpotRequestOptions options = new SpotRequestOptions();
        SpotRequestClient spotRequestClient = SpotRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        System.out.println(spotRequestClient.get24hrTickerPriceChange("SHIBUSDT"));
        // System.out.println(syncRequestClient.get24hrTickerPriceChange(null));
    }
}
