package com.bte.bipay.utils;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * 签名工具类
 */
public class SignUtil {

    public static String sign(String key,String timestamp,String username,String type,String body) throws Exception {
        return DigestUtils.md5(body + username + key + timestamp).toString();
    }

    public static String sign(String key,String timestamp,String username,String body) throws Exception {
        return DigestUtils.md5Hex(body + username + key + timestamp).toString();
    }

    public static String sign(String key,String timestamp,String nonce) throws Exception {
        return DigestUtils.md5Hex(key + nonce + timestamp ).toLowerCase();
    }
}
