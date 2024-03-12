package com.mzwise.common.util;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Slf4j
public class GoogleToken {

    private String privateKey;

    private int validTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expiredAt;


    public static GoogleToken createRandomPrivateKey(int validTime) {
        SecureRandom sr;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(Base64.decodeBase64("g8GjEvTbW5oVSV7avL47357438reyhreyuryetredLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx"));
            byte[] buffer = sr.generateSeed(10);
            Base32 codec = new Base32();
            byte[] bEncodedKey = codec.encode(buffer);
            String encodedKey = new String(bEncodedKey, StandardCharsets.UTF_8);

            return new GoogleToken(encodedKey, validTime);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("谷歌验证器生成秘钥失败:" + e.toString());
        }
    }


    private GoogleToken(String privateKey, int validTime) {

        this.privateKey = privateKey;
        this.validTime = validTime;
        this.expiredAt = LocalDateTime.now().plusSeconds(validTime);
    }

    private boolean isExpired() {
        return expiredAt.isBefore(LocalDateTime.now());
    }


    public boolean verify(long code) {

        if (!isExpired()) {
            Base32 codec = new Base32();
            byte[] decodedKey = codec.decode(this.getPrivateKey());
            long time = System.currentTimeMillis();
            long t = (time / 1000L) / 30L;  // TODO: ??comment here!!
            // 最多可偏移的时间
            // default 3 - max 17
            int window_size = 16; // 3~17 TODO: config?
            for (int i = -window_size; i <= window_size; ++i) {
                long hash;
                try {
                    hash = verifyCode(decodedKey, t + i);
                    if (hash == code) {
                        log.debug("谷歌验证成功");
                        return true;
                    }
                } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                    log.debug("谷歌验证失敗 {}", e.getMessage());
                    return false;
                }
            }
        }
        return false;
    }

    private static int verifyCode(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }


}