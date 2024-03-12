package com.mzwise.modules.distribution.service;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/05/21
 */
public interface ShareService {
    /**
     * 分享奖计算
     *
     * @param subMemberId
     * @param amount
     * @return
     */
    void addShareAmount(Long subMemberId, BigDecimal amount);
}
