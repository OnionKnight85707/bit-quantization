package com.mzwise.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


@SuppressWarnings("restriction")
public class RSAHelper {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 234;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    private static final String CHARSET ="UTF-8";

    /**
     * 公钥解密
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes =  (new BASE64Decoder()).decodeBuffer(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     *  公钥分段解密
     * @param encryptedData 加密的base64数据
     * @param publicKey rsa 公钥
     * @return
     */
    public static String decryptByPublicKey(String encryptedData, String publicKey){
        if(StringUtils.isBlank(encryptedData) || StringUtils.isBlank(publicKey)){
            return "";
        }

        try {
            encryptedData = encryptedData.replace("\r", "").replace("\n", "");
            Base64 decoder = new Base64(true);
            byte[] data = decryptByPublicKey(decoder.decode(encryptedData), publicKey);
            if(data == null || data.length < 1){
                return  "";
            }
            return new String(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 私钥加密
     *
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes =  (new BASE64Decoder()).decodeBuffer(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     *  私钥分段加密数据
     * @param data 待加密数据
     * @param privateKey  私钥
     * @return
     */
    public static String encryptByPrivateKey(String data, String privateKey){
        if(StringUtils.isBlank(data) || StringUtils.isBlank(privateKey)){
            return "";
        }

        try {
            byte[] encryptedData = encryptByPrivateKey(data.getBytes("UTF-8"), privateKey);
            if(encryptedData == null || encryptedData.length < 1){
                return  "";
            }

            Base64 encoder = new Base64(true);
            byte[] dataBytes = encoder.encode(encryptedData);
            return new String(dataBytes).replace("\r", "").replace("\n", "");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "";
    }
}