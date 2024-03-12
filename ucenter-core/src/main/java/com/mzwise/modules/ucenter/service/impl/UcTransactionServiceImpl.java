package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.TransactionStatusEnum;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcPartnerStat;
import com.mzwise.modules.ucenter.entity.UcRecharge;
import com.mzwise.modules.ucenter.entity.UcTransaction;
import com.mzwise.modules.ucenter.mapper.UcTransactionMapper;
import com.mzwise.modules.ucenter.service.UcTransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.vo.NameAndValueVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
@Service
public class UcTransactionServiceImpl extends ServiceImpl<UcTransactionMapper, UcTransaction> implements UcTransactionService {

    @Override
    public Page<UcTransaction> list(Long memberId, WalletTypeEnum walletType, TransactionTypeEnum transactionType, Integer pageNum, Integer pageSize) {
        Page<UcTransaction> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcTransaction> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcTransaction::getMemberId, memberId)
                .orderByDesc(UcTransaction::getId);
        if (walletType!=null) {
            wrapper.lambda().eq(UcTransaction::getWalletType, walletType);
        }
        if (transactionType!=null) {
            wrapper.lambda().eq(UcTransaction::getType, transactionType);
        }
        return page(page, wrapper);
    }

    /**
     * 新增交易记录
     *
     * @param memberId              会员id
     * @param walletTypeEnum        钱包类型
     * @param transactionTypeEnum   交易类型
     * @param amount                金额
     * @param fee                   手续费
     * @param transactionStatusEnum 状态
     * @param amountBeforeChange 变动前金额
     * @param amountAfterChange 变动后金额
     * @param remark 备注
     * @param remarkUser 给用户备注
     */
    @Override
    public void addTransactionRecord(Long memberId, WalletTypeEnum walletTypeEnum, TransactionTypeEnum transactionTypeEnum,
                                     BigDecimal amount, BigDecimal fee, TransactionStatusEnum transactionStatusEnum,
                                     BigDecimal amountBeforeChange, BigDecimal amountAfterChange, String remark, String remarkUser) {
        UcTransaction entity = new UcTransaction();
        entity.setMemberId(memberId);
        entity.setWalletType(walletTypeEnum);
        entity.setType(transactionTypeEnum);
        entity.setAmount(amount);
        entity.setFee(fee);
        entity.setStatus(transactionStatusEnum.getValue());
        entity.setAmountBeforeChange(amountBeforeChange);
        entity.setAmountAfterChange(amountAfterChange);
        entity.setRemark(remark);
        entity.setRemarkUser(remarkUser);
        entity.setRefId(remarkUser==null?0: Integer.parseInt(remarkUser));
        baseMapper.insert(entity);
    }

    /**
     * 资产明细事件列表
     *
     * @return
     */
    @Override
    public List<NameAndValueVo> transactionTypeList() {
        List<NameAndValueVo> list = new ArrayList<>();
        NameAndValueVo chargeQuantService = NameAndValueVo.builder().name(TransactionTypeEnum.CHARGE_QUANT_SERVICE.getName()).value(TransactionTypeEnum.CHARGE_QUANT_SERVICE.toString()).build();
        NameAndValueVo recharge = NameAndValueVo.builder().name(TransactionTypeEnum.RECHARGE.getName()).value(TransactionTypeEnum.RECHARGE.toString()).build();
        NameAndValueVo withdrawal = NameAndValueVo.builder().name(TransactionTypeEnum.WITHDRAWAL.getName()).value(TransactionTypeEnum.WITHDRAWAL.toString()).build();
        NameAndValueVo withdrawalFailedRefund = NameAndValueVo.builder().name(TransactionTypeEnum.WITHDRAWAL_FAILED_REFUND.getName()).value(TransactionTypeEnum.WITHDRAWAL_FAILED_REFUND.toString()).build();
        NameAndValueVo partnerCommission = NameAndValueVo.builder().name(TransactionTypeEnum.PARTNER_COMMISSION.getName()).value(TransactionTypeEnum.PARTNER_COMMISSION.toString()).build();
        NameAndValueVo registerAward = NameAndValueVo.builder().name(TransactionTypeEnum.REGISTER_AWARD.getName()).value(TransactionTypeEnum.REGISTER_AWARD.toString()).build();
        NameAndValueVo frozen = NameAndValueVo.builder().name(TransactionTypeEnum.FROZEN_QUANT_SERVICE.getName()).value(TransactionTypeEnum.FROZEN_QUANT_SERVICE.toString()).build();
        NameAndValueVo unFrozen = NameAndValueVo.builder().name(TransactionTypeEnum.UN_FROZEN_QUANT_SERVICE.getName()).value(TransactionTypeEnum.UN_FROZEN_QUANT_SERVICE.toString()).build();
        list.add(chargeQuantService);
        list.add(recharge);
        list.add(withdrawal);
        list.add(withdrawalFailedRefund);
        list.add(partnerCommission);
        list.add(registerAward);
        list.add(frozen);
        list.add(unFrozen);
        return list;
    }

    @Override
    public List<UcPartnerStat> findYesterdayCommissionStatistics(LocalDate startDay, LocalDate endDay) {
        return baseMapper.findYesterdayCommissionStatistics(startDay,endDay);
    }

    @Override
    public UcPartnerStat findYesterdayCommissionStatisticsByMember(LocalDate startDay, LocalDate endDay, int memberId) {
        return baseMapper.findYesterdayCommissionStatisticsByMember(startDay,endDay,memberId);
    }

}
