package com.mzwise.modules.ucenter.service;

import java.math.BigDecimal;

/**
 * 服务费服务
 */
public interface UcChargeService {
    /**
     * 收取服务费
     * @param memberId
     * @param amount
     */
    Boolean charge(Long memberId, BigDecimal amount);
}
