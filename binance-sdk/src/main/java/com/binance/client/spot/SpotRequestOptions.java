package com.binance.client.spot;

import com.binance.client.spot.constant.SpotBinanceApiConstants;
import com.binance.client.spot.exception.BinanceApiException;
import java.net.URL;

/**
 * The configuration for the request APIs
 */
public class SpotRequestOptions {

    private String url = SpotBinanceApiConstants.API_BASE_URL;

    public SpotRequestOptions() {
    }

    public SpotRequestOptions(SpotRequestOptions option) {
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
