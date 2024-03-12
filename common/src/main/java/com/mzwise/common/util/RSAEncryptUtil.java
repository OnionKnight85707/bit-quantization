package com.mzwise.common.util;

import com.alibaba.fastjson.JSONObject;
import com.mzwise.common.api.CommonResult;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RSAEncryptUtil {
    public static BASE64Encoder base64Encoder = new BASE64Encoder();
    public static BASE64Decoder base64Decoder = new BASE64Decoder();

    public static final String KEY_ALGORITHM = "RSA";

    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";

    private static final String PRIVATE_KEY = "RSAPrivateKey";
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    public static String enc( String str, String rsaPublicKey)
    {
        String result = "";
        try {
            // 将Base64编码后的公钥转换成PublicKey对象
            byte[] buffer = new Base64().decode(rsaPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            // 加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] inputArray = str.getBytes();
            int inputLength = inputArray.length;
            System.out.println("加密字节数：" + inputLength);
            // 最大加密字节数，超出最大字节数需要分组加密
            int MAX_ENCRYPT_BLOCK = 117;
            // 标识
            int offSet = 0;
            byte[] resultBytes = {};
            byte[] cache = {};
            while (inputLength - offSet > 0) {
                if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                    offSet += MAX_ENCRYPT_BLOCK;
                } else {
                    cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                    offSet = inputLength;
                }
                resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
                System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
            }
            result = new Base64().encodeToString(resultBytes);
        } catch (Exception e) {
            System.out.println("rsaEncrypt error:" + e.getMessage());
        }
        System.out.println("加密的结果：" + result);
        return result;
    }

    // 私钥加密
    public static String encryptByPrivateKey(String content,String privateKeyStr) throws Exception {
        // 获取私钥
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] cipherText = cipher.doFinal(content.getBytes());
        String cipherStr = base64Encoder.encode(cipherText);
        return cipherStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    // 将base64编码后的私钥字符串转成PrivateKey实例
    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = base64Decoder.decodeBuffer(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    // 公钥解密
    public static String decryptByPublicKey(String content,String publicKeyStr) throws Exception {
        // 获取公钥
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] cipherText = base64Decoder.decodeBuffer(content);
        byte[] decryptText = cipher.doFinal(cipherText);
        return new String(decryptText);
    }

    // 将base64编码后的公钥字符串转成PublicKey实例
    private static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = base64Decoder.decodeBuffer(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static Map initKey() throws Exception {

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map keyMap = new HashMap(2);

        keyMap.put(PUBLIC_KEY, publicKey);

        keyMap.put(PRIVATE_KEY, privateKey);

        return keyMap;

    }

    public static byte[] decryptBASE64(String key) throws Exception {

        return (new BASE64Decoder()).decodeBuffer(key);

    }

    public static String encryptBASE64(byte[] key) throws Exception {

        return (new BASE64Encoder()).encodeBuffer(key);

    }


    public static String getPublicKey(Map keyMap) throws Exception {

        Key key = (Key) keyMap.get(PUBLIC_KEY);

        byte[] publicKey = key.getEncoded();

        return encryptBASE64(key.getEncoded());

    }

    public static String getPrivateKey(Map keyMap) throws Exception {

        Key key = (Key) keyMap.get(PRIVATE_KEY);

        byte[] privateKey = key.getEncoded();

        return encryptBASE64(key.getEncoded());

    }

    public static void main(String[] args) throws Exception {

        Map map=initKey();
        String publickey=getPublicKey(map);
        String privateKey=getPrivateKey(map);
        System.out.printf(" public: "+publickey+"  private:"+privateKey);

        CommonResult result=CommonResult.failed("test");
        String text=JSONObject.toJSONString(result);
        String en=enc(text,publickey);

        String de=decrypt(en,privateKey);
        System.out.println("text: "+text);
        System.out.println(" en: "+en);
        System.out.println("de:"+de);
    }

}
