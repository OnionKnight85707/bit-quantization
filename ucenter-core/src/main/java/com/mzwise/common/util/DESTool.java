package com.mzwise.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import static javax.crypto.Cipher.*;

/**基于DES算法进行对称加密工具类
 * @Author: kevin
 */
public class DESTool {



    // 向量(同时拥有向量和密匙才能解密)，此向量必须是8byte，多少都报错
    private final byte[] VECTOR = new byte[] { 0x22, 0x54, 0x36, 110, 0x40, (byte) 0xac, (byte) 0xad, (byte) 0xdf };
    /**
     * 加密算法的参数接口
     */
    private AlgorithmParameterSpec iv;
    private Key key;
    private final String charset = "utf-8";


    /**
     * 构造函数
     * @param deSkey 长度至少>=8位的字符串秘钥
     * @throws Exception
     */
    public DESTool(String deSkey) {
        if (StringUtils.isBlank(deSkey)|| deSkey.length()<8) {
            throw new IllegalArgumentException("秘钥长度至少为不小于8位的字符串");
        }
        try {
            DESKeySpec keySpec = new DESKeySpec(deSkey.getBytes(this.charset));// 设置密钥参数
            iv = new IvParameterSpec(VECTOR);// 设置向量
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂

            key = keyFactory.generateSecret(keySpec);// 得到密钥对象
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 加密
     * @param data
     * @return
     * @throws Exception
     */
    public  String encode(String data) throws Exception {
        Cipher enCipher = getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
        enCipher.init(ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
        byte[] pasByte = enCipher.doFinal(data.getBytes(this.charset));
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(pasByte);
    }

    /**
     * 解密
     * @param data
     * @return
     * @throws Exception
     */
    public String decode(String data) throws Exception {
        if (data == null) {
            return null;
        }
        Cipher deCipher = getInstance("DES/CBC/PKCS5Padding");
        deCipher.init(DECRYPT_MODE, key, iv);
        BASE64Decoder base64Decoder = new BASE64Decoder();
        // 此处注意doFinal()的参数的位数必须是8的倍数，否则会报错（通过encode加密的字符串读出来都是8的倍数位，但写入文件再读出来，
        // 就可能因为读取的方式的问题，导致最后此处的doFinal()的参数的位数不是8的倍数）
        // 此处必须用base64Decoder，若用data。getBytes()则获取的字符串的byte数组的个数极可能不是8的倍数，
        // 而且不与上面的BASE64Encoder对应（即使解密不报错也不会得到正确结果）
        byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));
        return new String(pasByte, this.charset);
    }


    public static Key getKey() throws NoSuchAlgorithmException {
        Key kp = null;
        SecureRandom sr = new SecureRandom();
        KeyGenerator kg = KeyGenerator.getInstance("DES");
        kg.init(sr);
        // 相对路径 需要新建 conf 文件夹
        // String fileName = "conf/DesKey.xml";
        // 绝对路径
        Key key = kg.generateKey();
        return key;
    }


}
