package com.mzwise.unifyexchange.util;

import java.math.BigDecimal;

/**
 * @Author wmf
 * @Date 2021/7/8 17:55
 * @Description
 */
public class CommonUtil {

    public static Integer scaleBySize(BigDecimal size) {
        int number = BigDecimal.ONE.divide(size).intValue();
        int count = 0;
        while (number > 1) {
            number = number / 10;
            count++;
        }
        return count;
    }
}
