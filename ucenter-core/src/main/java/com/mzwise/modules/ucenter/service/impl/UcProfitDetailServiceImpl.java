package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.constant.TransactionStatusEnum;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.UnitEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcProfitDetail;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.modules.ucenter.mapper.UcProfitDetailMapper;
import com.mzwise.modules.ucenter.mapper.UcWalletMapper;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcProfitDetailService;
import com.mzwise.modules.ucenter.service.UcTransactionService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import com.mzwise.modules.ucenter.vo.DailyEarningsStatisticsVo;
import com.mzwise.modules.ucenter.vo.ProfitDetailsVo;
import com.mzwise.modules.ucenter.vo.UserChargesResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 用户收益详情实现类
 * @Author LiangZaiChao
 * @Date 2022/7/5 11:45
 */
@Slf4j
@Service
public class UcProfitDetailServiceImpl extends ServiceImpl<UcProfitDetailMapper, UcProfitDetail> implements UcProfitDetailService {

    @Autowired
    private UcWalletService walletService;
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcTransactionService transactionService;
    @Autowired
    private UcWalletMapper walletMapper;

    /**
     * 记录收益详情
     *
     * @param serviceFeeIsSuccess 服务费是否收取成功：true：成功， false：失败
     * @param memberId 用户id
     * @param quantId 量化id
     * @param orderId 订单id
     * @param profit 利润
     * @param amount 金额
     */
    @Override
    public void recordDetail(boolean serviceFeeIsSuccess, Long memberId, Long quantId, String orderId, BigDecimal profit, BigDecimal amount) {
        UcProfitDetail detail = new UcProfitDetail();
        detail.setMemberId(memberId);
        detail.setQuantId(quantId);
        detail.setOrderId(orderId);
        detail.setProfit(profit);
        detail.setAmount(amount);
        detail.setIsDeduct(serviceFeeIsSuccess);
        detail.setCreateTime(new Date());
        baseMapper.insert(detail);
    }

    /**
     * 查询昨天交易汇总
     *
     * @return
     */
    @Override
    public List<DailyEarningsStatisticsVo> findYesterdayEarningsStatistics() {
        return baseMapper.selYesterdayEarningsStatistics();
    }

    /**
     * 补缴欠款
     *
     * @param memberId 用户id
     * @param isProcessPartnerCommission 是否处理合伙人佣金
     * @return 补发是否成功：true成功
     */
    @Override
    @Transactional
    public void repayArrears(Long memberId, boolean isProcessPartnerCommission) {
        UcMember member = memberService.getById(memberId);
        if (BigDecimal.ZERO.compareTo(member.getUnPay()) >= 0) {
            log.info("补缴欠款(无需补缴欠款)：用户id={}，欠款={}", member.getId(), member.getUnPay());
            return;
        }
        // 平台服务费比例
        BigDecimal chargeServiceRate = walletMapper.getPlatformServiceRate();
        // 未扣费的收益记录, uc_profit_detail的欠费记录按时间顺序，先扣除时间最久的一笔（每笔要么全扣，要么不扣）
        List<UcProfitDetail> unDeductList = this.list(Wrappers.<UcProfitDetail>query().lambda()
                .eq(UcProfitDetail::getMemberId, memberId).eq(UcProfitDetail::getIsDeduct, false));
        if (CollectionUtils.isEmpty(unDeductList)) {
            log.info("补缴欠款:用户={}，时间={}，没有需要补缴的欠款记录。", memberId, LocalDateTime.now());
            return;
        }
        for (UcProfitDetail temp : unDeductList) {
            // 需要收取的服务费
            BigDecimal serviceCharge = temp.getProfit().multiply(chargeServiceRate).setScale(8, BigDecimal.ROUND_HALF_UP);
            // 实时获取钱包
            UcWallet masterWallet = walletService.getMasterWallet(UnitEnum.USDT.getName(), memberId);
            if (masterWallet.getBalance().add(masterWallet.getTicket()).compareTo(serviceCharge) == -1) {
                log.info("补缴欠款(不够支付欠款)：uc_profit_detail id={}，用户={}，钱包余额={}，奖励金额={}, 不够支付欠款={}",
                        temp.getId(), memberId, masterWallet.getBalance(), masterWallet.getTicket(), serviceCharge);
                return;
            }
            // 用户钱包扣费
//            walletService.subtractBalance(masterWallet.getId(), serviceCharge);
            UserChargesResp userChargesResp = walletService.userCharges(masterWallet, serviceCharge, temp.getProfit());
            // 平台收费
            walletService.platformCharges(serviceCharge);
            // 减掉欠款
            memberService.reduceArrears(memberId, serviceCharge);
            String remark = "系统自动补缴欠款：用户=" + memberId + "，系统自动扣除欠款=" + serviceCharge + ", uc_profit_detail id=" + temp.getId();
            // 记录流水
            transactionService.addTransactionRecord(memberId, WalletTypeEnum.QUANT_SERVICE, TransactionTypeEnum.CHARGE_QUANT_SERVICE,
                    serviceCharge.multiply(new BigDecimal("-1")), BigDecimal.ZERO, TransactionStatusEnum.SUCCESS, masterWallet.getBalance().add(masterWallet.getTicket()),
                    masterWallet.getBalance().add(masterWallet.getTicket()).subtract(serviceCharge), remark, null);
            // 更新uc_profit_detail扣费状态为已扣费
            temp.setIsDeduct(true);
            baseMapper.updateById(temp);
            if (isProcessPartnerCommission) {
                // 补发合伙人佣金
                memberService.handlePartnerCommission(true, memberId, temp.getOrderId(),
                        userChargesResp.getDeductionAward(), userChargesResp.getActualCalcCommissionProfit(), temp.getProfit());
            }
        }
        // unDeductList都补缴完成，最后查询用户欠费总记录数，如果为0，代表没欠费，需要把uc_member的欠费提醒次数(reminder_times)置为0
        int unDeductCount = this.count(Wrappers.<UcProfitDetail>query().lambda().eq(UcProfitDetail::getMemberId, memberId).eq(UcProfitDetail::getIsDeduct, false));
        if (unDeductCount == 0) {
            UcMember payedMember = memberService.getById(memberId);
            if (payedMember.getUnPay().compareTo(new BigDecimal("0.01")) <= 0) {
                // 如果欠费记录为0的情况，un_pay还有值 & 小于 0.01，直接减去un_pay
                memberService.reduceArrears(memberId, payedMember.getUnPay());
                log.info("补缴欠款：欠费记录为0，un_pay={}，用户={}", payedMember.getUnPay(), payedMember.getId());
            }
            if (payedMember.getReminderTimes() != 0) {
                // uc_member的欠费提醒次数(reminder_times)置为0
                memberService.reminderTimesToZero(memberId);
            }
        }
    }

    /**
     * 收益详情
     *
     * @param pageNum
     * @param pageSize
     * @param memberId
     * @return
     */
    @Override
    public Page<ProfitDetailsVo> profitDetails(Integer pageNum, Integer pageSize, Long memberId) {
        Page<ProfitDetailsVo> page = new Page<>(pageNum, pageSize);
        return baseMapper.profitDetails(page, memberId);
    }

    /**
     * 按日统计收益
     *
     * @param pageNum
     * @param pageSize
     * @param memberId
     * @return
     */
    @Override
    public Page<ProfitDetailsVo> profitDetailsForDay(Integer pageNum, Integer pageSize, Long memberId) {
        Page<ProfitDetailsVo> page = new Page<>(pageNum, pageSize);
        return baseMapper.profitDetailsForDay(page, memberId);
    }

    /**
     * 月度统计收益
     *
     * @param pageNum
     * @param pageSize
     * @param memberId
     * @return
     */
    @Override
    public Page<ProfitDetailsVo> profitDetailsForMonth(Integer pageNum, Integer pageSize, Long memberId) {
        Page<ProfitDetailsVo> page = new Page<>(pageNum, pageSize);
        return baseMapper.profitDetailsForMonth(page, memberId);
    }

    /**
     * 年度统计收益
     *
     * @param pageNum
     * @param pageSize
     * @param memberId
     * @return
     */
    @Override
    public Page<ProfitDetailsVo> profitDetailsForYear(Integer pageNum, Integer pageSize, Long memberId) {
        Page<ProfitDetailsVo> page = new Page<>(pageNum, pageSize);
        return baseMapper.profitDetailsForYear(page, memberId);
    }

}
