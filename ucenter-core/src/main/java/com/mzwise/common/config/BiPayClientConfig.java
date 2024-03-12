package com.mzwise.common.config;

import com.bte.bipay.http.client.BiPayClient2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BiPayClientConfig {

    @Value("${bipay.gateway}")
    private String gateway;

    @Value("${bipay.merchantId}")
    private String merchantId;

    @Value("${bipay.merchantKey}")
    private String merchantKey;

    @Value("${bipay.callback}")
    private String callbackIP;

    @Bean
//    @ConfigurationProperties(prefix = "bipay")
    public BiPayClient2 setBiPayClient(){
        BiPayClient2 client = new BiPayClient2(gateway, merchantId, merchantKey, callbackIP);
        return client;
    }
}
