package com.mzwise.common.config;

import com.mzwise.engine.CoinMatchFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CoinMatchFactoryConfig {
    @Bean
    public CoinMatchFactory getContractCoinMatchFactory() {

        CoinMatchFactory factory = new CoinMatchFactory();
        return factory;

    }
}
