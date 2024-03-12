package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.entity.UcProfit;
import com.mzwise.modules.ucenter.vo.UserProfitGeneralVO;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-04-01
 */
public interface UcProfitService extends IService<UcProfit> {
    /**
     * 个人收益概览
     * @param memberId
     * @return
     */
    UserProfitGeneralVO general(Long memberId);

    /**
     * 更新合约收益
     * @param memberId 用户id
     * @param profit 收益
     */
    void updateSwapProfit(Long memberId, BigDecimal profit);

}
