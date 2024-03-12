package com.mzwise.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ComputeUtil {
//    /**
//     * 根据步长获取最大金额, 并调整至精度
//     * @param amount
//     * @param step
//     * @return
//     */
//    public static BigDecimal amountByStep(BigDecimal amount, BigDecimal step, Integer scale) {
//        BigDecimal[] divideAndRemainder = amount.divideAndRemainder(step);
//        return divideAndRemainder[0].multiply(step).setScale(scale, RoundingMode.DOWN);
//    }

    /**
     * 删除无用小数点0
     * @param amount
     * @return
     */
    public static BigDecimal stripZeroes(BigDecimal amount) {
        return new BigDecimal(amount.stripTrailingZeros().toPlainString());
    }
}
