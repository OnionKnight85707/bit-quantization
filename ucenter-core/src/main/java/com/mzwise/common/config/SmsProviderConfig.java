package com.mzwise.common.config;

import com.mzwise.common.provider.support.AliYunSMSProvider;
import com.mzwise.common.provider.SMSProvider;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsProviderConfig {

    @Value("${sms.gateway:}")
    private String gateway;
    @Value("${sms.username:}")
    private String username;
    @Value("${sms.password:}")
    private String password;
    @Value("${sms.sign:}")
    private String sign;
    @Value("${sms.internationalGateway:}")
    private String internationalGateway;
    @Value("${sms.internationalUsername:}")
    private String internationalUsername;
    @Value("${sms.internationalPassword:}")
    private String internationalPassword;
    @Value("${access.key.id:}")
    private String accessKey;
    @Value("${access.key.secret:}")
    private String accessSecret;


    @Bean
    public SMSProvider getSMSProvider(@Value("${sms.driver:}") String driverName) {
        //默认发送：阿里云信息
        if (StringUtil.isNullOrEmpty(driverName)) {
            return new AliYunSMSProvider(gateway, username, password, sign);
        }
        if (driverName.equalsIgnoreCase(AliYunSMSProvider.getName())) {
            return new AliYunSMSProvider(gateway, username, password, sign);
        } else {
            return null;
        }
    }
}
