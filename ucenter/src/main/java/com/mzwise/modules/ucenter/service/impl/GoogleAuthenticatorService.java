package com.mzwise.modules.ucenter.service.impl;


import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.util.GoogleToken;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class GoogleAuthenticatorService {

    private final int validTime=3600;
     @Value("${encryption.gg}")
    private String key;

    public String getKey()
    {
        return key;
    }

    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;
    /**
     * @return
     */
    public GoogleToken generate() {
        GoogleToken googleToken = GoogleToken.createRandomPrivateKey(validTime);
        return googleToken;
    }


    public boolean verify(String code)
    {
        return verifyPrivateKey(getKey(),code);
    }


    public boolean checkCode(String code)
    {
        return verifyPrivateKey2(getKey(),code);
    }

    /**
     * 验证参数指定的 谷歌验证码 和谷歌私钥是否匹配
     * @param privateKey
     * @param authCode
     * @return
     */
    public  boolean verifyPrivateKey(String privateKey, String authCode) {

        GoogleToken googleToken = new GoogleToken();
        googleToken.setPrivateKey(privateKey);
        googleToken.setExpiredAt(LocalDateTime.now().plusSeconds(5));

        try {
            if (googleToken.verify(Long.parseLong(authCode))) {

                return true;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.GoogleAuthenticatorService_001));
        }


        throw new IllegalArgumentException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.GoogleAuthenticatorService_001));



    }

    public  boolean verifyPrivateKey2(String privateKey, String authCode) {

        GoogleToken googleToken = new GoogleToken();
        googleToken.setPrivateKey(privateKey);
        googleToken.setExpiredAt(LocalDateTime.now().plusSeconds(5));


        if (googleToken.verify(Long.parseLong(authCode))) {

            return true;

        }
        return false;

    }


}
