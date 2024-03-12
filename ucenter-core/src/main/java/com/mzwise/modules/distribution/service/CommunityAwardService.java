package com.mzwise.modules.distribution.service;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/05/21
 */
public interface CommunityAwardService {
    /**
     * 增加社区奖
     *
     * @param memberId
     * @param amount
     */
    void addCommunityAward(Long memberId, BigDecimal amount);


}
