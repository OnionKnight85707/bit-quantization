package com.mzwise.common.util;

import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.mapper.UcWalletMapper;

import java.math.BigDecimal;

public class WalletProfitUtil {

    private static UcWalletMapper ucWalletMapper;

    static {
        ucWalletMapper = (UcWalletMapper) SpringUtil.getBean("ucWalletMapper");
    }

    /**
     * 新增钱包收益
     * @param memberId
     * @param type
     * @param amount
     * @return
     */
    public static Boolean add(Long memberId, WalletTypeEnum type, BigDecimal amount) {
        return ucWalletMapper.addProfit(memberId, type, amount) > 0;
    }



    /**
     * 设置收益数据
     * @param memberId
     * @param type
     * @param todayProfit
     * @param totalProfit
     * @return
     */
    public static Boolean setProfit(Long memberId, WalletTypeEnum type, BigDecimal todayProfit, BigDecimal totalProfit) {
        return ucWalletMapper.setProfit(memberId, type, todayProfit, totalProfit) > 0;
    }

    /**
     * 清除当日收益
     * @return
     */
    public static Boolean resetTodayProfit() {
        return ucWalletMapper.resetTodayProfit() > 0;
    }
}
