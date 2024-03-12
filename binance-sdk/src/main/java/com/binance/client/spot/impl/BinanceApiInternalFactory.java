package com.binance.client.spot.impl;

import com.binance.client.spot.SpotRequestOptions;
import com.binance.client.spot.SubscriptionClient;
import com.binance.client.spot.SubscriptionOptions;
import com.binance.client.spot.SpotRequestClient;
import java.net.URI;

public final class BinanceApiInternalFactory {

    private static final BinanceApiInternalFactory instance = new BinanceApiInternalFactory();

    public static BinanceApiInternalFactory getInstance() {
        return instance;
    }

    private BinanceApiInternalFactory() {
    }

    public SpotRequestClient createSyncRequestClient(String apiKey, String secretKey, SpotRequestOptions options) {
        SpotRequestOptions spotRequestOptions = new SpotRequestOptions(options);
        RestApiRequestImpl requestImpl = new RestApiRequestImpl(apiKey, secretKey, spotRequestOptions);
        return new SpotRequestImpl(requestImpl);
    }

    public SubscriptionClient createSubscriptionClient(String apiKey, String secretKey, SubscriptionOptions options) {
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions(options);
        SpotRequestOptions spotRequestOptions = new SpotRequestOptions();
        try {
            String host = new URI(options.getUri()).getHost();
            spotRequestOptions.setUrl("https://" + host);
        } catch (Exception e) {

        }
        SubscriptionClient webSocketStreamClient = new WebSocketStreamClientImpl(apiKey, secretKey,
                subscriptionOptions);
        return webSocketStreamClient;
    }

}
