package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.TransactionStatusEnum;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcPartnerStat;
import com.mzwise.modules.ucenter.entity.UcTransaction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.vo.DailyEarningsStatisticsVo;
import com.mzwise.modules.ucenter.vo.NameAndValueVo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
public interface UcTransactionService extends IService<UcTransaction> {

    /**
     * 资产明细查找
     * @param currentUserId
     * @param walletType
     * @param transactionType
     * @param page
     * @param size
     * @return
     */
    Page<UcTransaction> list(Long currentUserId, WalletTypeEnum walletType, TransactionTypeEnum transactionType, Integer page, Integer size);

    /**
     * 新增交易记录
     * @param memberId 会员id
     * @param walletTypeEnum 钱包类型
     * @param transactionTypeEnum 交易类型
     * @param amount 金额
     * @param fee 手续费
     * @param transactionStatusEnum 状态
     * @param amountBeforeChange 变动前金额
     * @param amountAfterChange 变动后金额
     * @param remark 备注
     * @param remarkUser 给用户备注
     */
    void addTransactionRecord(Long memberId, WalletTypeEnum walletTypeEnum, TransactionTypeEnum transactionTypeEnum,
                              BigDecimal amount, BigDecimal fee, TransactionStatusEnum transactionStatusEnum,
                              BigDecimal amountBeforeChange, BigDecimal amountAfterChange, String remark, String remarkUser);

    /**
     * 资产明细事件列表
     * @return
     */
    List<NameAndValueVo> transactionTypeList();


    List<UcPartnerStat> findYesterdayCommissionStatistics(LocalDate startDay, LocalDate endDay);


    UcPartnerStat findYesterdayCommissionStatisticsByMember(LocalDate startDay, LocalDate endDay,int memberId);
}
