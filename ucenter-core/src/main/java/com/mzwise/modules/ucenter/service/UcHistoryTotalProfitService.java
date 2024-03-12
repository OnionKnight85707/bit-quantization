package com.mzwise.modules.ucenter.service;

import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcHistoryTotalProfit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2021-05-27
 */
public interface UcHistoryTotalProfitService extends IService<UcHistoryTotalProfit> {

    /**
     * 创建或更新历史总收益
     *
     * @param memberId       会员id
     * @param walletTypeEnum 钱包类型
     * @param amount         金额
     */
    void savaOrUpdate(Long memberId, WalletTypeEnum walletTypeEnum, BigDecimal amount);

    /**
     * 通锅会员id查找用户历史总收益
     *
     * @param memberId 会员id
     * @return 历史总收益
     */
    UcHistoryTotalProfit getByMemberId(Long memberId);

    /**
     * 计算我的团队量化总收益
     * @param memberIdList 我下级的所有用户ids
     * @return
     */
    BigDecimal calMyTeamHistoryTotalProfit(List<Long> memberIdList);

    /**
     * 计算我的团队平台币bte总收益
     * @param memberIdList 我下级的所有用户ids
     * @return
     */
    BigDecimal calMyTeamBTEHistoryTotalProfit(List<Long> memberIdList);
}
