package com.mzwise.common.config;

import com.mzwise.common.util.RSAEncryptUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RsaService {

    @Value("${rsa.publickey}")
    private String pubKey;


    public String enCode(String text) throws Exception {
        return RSAEncryptUtil.enc(text,pubKey);
    }

}
