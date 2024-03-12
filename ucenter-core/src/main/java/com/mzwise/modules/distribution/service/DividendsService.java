package com.mzwise.modules.distribution.service;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/05/21
 */
public interface DividendsService {
    /**
     * 增加股东/董事分红收益
     *
     * @param memberId
     * @param amount
     */
    void addDividendsProfit(Long memberId, BigDecimal amount);
}
