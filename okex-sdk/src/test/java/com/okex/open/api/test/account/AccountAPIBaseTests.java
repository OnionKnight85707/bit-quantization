package com.okex.open.api.test.account;

import com.okex.open.api.config.APIConfiguration;
import com.okex.open.api.enums.I18nEnum;
import com.okex.open.api.test.BaseTests;

/**
 * Account api basetests
 *
 * @author hucj
 * @version 1.0.0
 * @date 2018/7/04 18:23
 */
public class AccountAPIBaseTests extends BaseTests {

    public APIConfiguration config() {
        APIConfiguration config = new APIConfiguration();

        config.setEndpoint("https://www.okex.com/");
        // apiKey，api注册成功后页面上有


        config.setApiKey("e68215fb-79b7-4990-815c-32ea089271ed");
        config.setSecretKey("B7CAC414F9F12EB8308FF3A1CCD13C5E");
        config.setPassphrase("Xiaoming58");


        config.setPrint(true);
        config.setI18n(I18nEnum.ENGLISH);

        return config;
    }


}
