package com.mzwise.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class GeneratorUtil {

    //匹配规则
    private static final String REGEX_OF_EMAIL="(^\\S)\\S.*(@)";

    //替换规则
    private static final String REPLACE_OF_EMAIL="$1***$2";

    //匹配规则
    private static final String REGEX_OF_PHONE = "(^\\d{3})\\d.*(\\d{4}$)";

    //替换规则
    private static final String REPLACE_OF_PHONE = "$1****$2";

    /**
     * 得到from到to的随机数，包括to
     *
     * @param from
     * @param to
     * @return
     */
    public static int getRandomNumber(int from, int to) {
        float a = from + (to - from) * (new Random().nextFloat());
        int b = (int) a;
        return ((a - b) > 0.5 ? 1 : 0) + b;
    }

    /**
     * 根据用户ID获取推荐码
     *
     * @param uid
     * @return
     */
    public static String getPromotionCode(Long uid) {
        String seed = "E5FCDG3HQA4B1NOPIJ2RSTUV67MWX89KLYZ";
        long num = uid + 10000;
        long mod = 0;
        StringBuffer code = new StringBuffer();
        while (num > 0) {
            mod = num % 35;
            num = (num - mod) / 35;
            code.insert(0, seed.charAt(Integer.parseInt(String.valueOf(mod))));
        }
        while (code.length() < 4) {
            code.insert(0, "0");
        }
        return code.toString();
    }

    public static String getNonceString(int len) {
        String seed = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < len; i++) {
            tmp.append(seed.charAt(getRandomNumber(0, 61)));
        }
        return tmp.toString();
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String getOrderId(String prefix) {
        String body = String.valueOf(System.currentTimeMillis());
        return prefix + body + getRandomNumber(10, 99);
    }

    /**
     * 邮箱脱敏
     * @param email
     * @return
     */
    public static String emailMasking(String email){
        if( ! StringUtils.isBlank(email)){
            return email.replaceAll(REGEX_OF_EMAIL, REPLACE_OF_EMAIL);
        }
        return null;
    }

    /**
     * 手机号脱敏
     * @param phone
     * @return
     */
    public static String phoneMasking(String phone){
        if( ! StringUtils.isBlank(phone)){
            return phone.replaceAll(REGEX_OF_PHONE, REPLACE_OF_PHONE);
        }
        return null;
    }

    /**
     * 获取指定区间的随机数
     * @param min 区间内最小值
     * @param max 区间内最大值
     * @return
     */
    public static int getRandom(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

    /**
     * 获取指定区间的随机数 （3位小数）
     * @param min 区间内最小值
     * @param max 区间内最大值
     * @param decimalPlaces 小数位
     * @return
     */
    public static BigDecimal getRandomBigDecimal(int min, int max, int decimalPlaces) {
        return (new BigDecimal(Math.random() * (max - min + 1) + min).setScale(decimalPlaces, BigDecimal.ROUND_HALF_DOWN));
    }

    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
//            System.out.println(new BigDecimal(Math.random()).setScale(5, BigDecimal.ROUND_HALF_UP));
            System.out.println(new BigDecimal(Math.random() * (20 - 1 + 1) + 1).setScale(3, BigDecimal.ROUND_HALF_DOWN));
        }
    }

}