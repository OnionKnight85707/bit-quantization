package com.binance.client.future;

import com.binance.client.future.constant.FutureBinanceApiConstants;
import com.binance.client.future.exception.BinanceApiException;
import java.net.URL;

/**
 * The configuration for the request APIs
 */
public class FutureRequestOptions {

    private String url = FutureBinanceApiConstants.API_BASE_URL;

    public FutureRequestOptions() {
    }

    public FutureRequestOptions(FutureRequestOptions option) {
        this.url = option.url;
    }

    /**
     * Set the URL for request.
     *
     * @param url The URL name like "https://fapi.binance.com".
     */
    public void setUrl(String url) {
        try {
            URL u = new URL(url);
            this.url = u.toString();
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.INPUT_ERROR, "The URI is incorrect: " + e.getMessage());
        }
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}