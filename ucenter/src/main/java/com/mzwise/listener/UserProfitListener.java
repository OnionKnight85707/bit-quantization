package com.mzwise.listener;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mzwise.common.domain.ProfitData;
import com.mzwise.constant.SettleTypeEnum;
import com.mzwise.modules.ucenter.entity.UcProfitDetail;
import com.mzwise.modules.ucenter.service.*;
import com.mzwise.modules.ucenter.vo.UserChargesResp;
import com.mzwise.quant.rpc.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * 用户收益监听
 * @author Administrator
 */
@Component
@Slf4j
@RabbitListener(containerFactory = "rabbitContainerFactory", bindings = {@QueueBinding(value = @Queue(value = "topic.user.profit", durable="true"), exchange = @Exchange(value = "exchange", type = "topic"))})
public class UserProfitListener {

    @Autowired
    private UcProfitDetailService profitDetailService;
    @Autowired
    private UcWalletService walletService;
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcStatisticsService statisticsService;

    /**
     * i)settle_type=1 实时结算，流程跟以前一样
     * ii)用户看到的在这里：settle_type=2 且 settled=false, 冻结结算模式，且当前策略没有结算（只发送正收益）：
     * 做1 2 3，更新uc_quant利润，profit_detail表需要记录
     * iii)平台看到的在这里：settle_type=2 且 settled=true 冻结结算模式，且当前策略已经结算（达到用户预期收益，发一条总的(有正有负收益的汇总)）：
     * 按照冻结比例ratio单独增加公司收入，分配合伙人收益，统计：增加用户合约收益
     * 1.不用扣除用户的服务费(扣除用户服务费的资金明细也不用插入)
     * 2.公司收入也不加
     * 3.合伙人利润也不分配
     * @param msg
     */
    @RabbitHandler
    public void process(String msg) {
        log.info("收到 用户 盈利消息： msg={}",msg);
        ProfitData profitData = JSONObject.parseObject(msg, ProfitData.class);
        Long memberId = Long.parseLong(profitData.getMemberId());
        Long quantId = Long.valueOf(profitData.getQuantId());
        String orderId = profitData.getOrderId();
        BigDecimal profit = new BigDecimal(profitData.getProfit());
        BigDecimal amount = new BigDecimal(profitData.getAmount());
        // 收益结算方式类型: 1:实时结算, 2:冻结结算
        Integer settleType = profitData.getSettleType();
        // 是否结算(适用于settleType=2:冻结结算 情况)
        boolean isSettled = profitData.isSettled();
        // 冻结平仓收益(冻结结算模式下)
        BigDecimal frozenRatio = new BigDecimal(profitData.getRatio());

        // 如果收到重复的订单，不处理（冻结结算模式汇总发的时候没有orderId，默认生产者只发一次）
        if ( ! ObjectUtils.isEmpty(orderId)) {
            int orderCount = profitDetailService.count(Wrappers.<UcProfitDetail>query().lambda().eq(UcProfitDetail::getOrderId, orderId));
            if (orderCount != 0) {
                log.info("UserProfitListener盈利重复信息：memberId={}, quantId={}, orderId={}, profit={}, amount={}", memberId, quantId, orderId, profit, amount);
                return;
            }
        }

        if (SettleTypeEnum.BY_FREEZE.getValue().equals( settleType) && ! isSettled) {
            // 冻结结算模式 && 没有结算 : 需要记录 1.更新uc_quant利润, 2.记录uc_profit_detail收益详情
            // 更新uc_quant利润
            walletService.updateProfit(memberId, orderId, profit);
            // 记录uc_profit_detail收益详情
            profitDetailService.recordDetail(true, memberId, quantId, orderId, profit, amount);
        } else if (SettleTypeEnum.BY_FREEZE.getValue() .equals( settleType)  && isSettled) {
            // 冻结结算模式 && 已经结算 (代表发了一条汇总的收益): 需要做：1.平台单独收费(用户不用扣费, 因为已经事先冻结了), 2.处理合伙人收益, 3.统计：增加用户合约收益
            // 平台单独收费(用户不用扣费, 因为已经事先冻结了)
            walletService.platformCharges(profit.multiply(frozenRatio));
            // 处理合伙人收益(因为用户已经事先冻结了钱，所以默认可以走合伙人逻辑)
            memberService.handlePartnerCommission(true, memberId, orderId, amount, profit, profit);
            // 统计：增加用户合约收益
            statisticsService.addUserSwapProfit(profit);
        } else if (SettleTypeEnum.BY_REALTIME.getValue() .equals( settleType) ) {
            // 如果是实时结算模式，按之前流程全部都要处理
            // 平台收取服务费
            UserChargesResp userChargesResp = walletService.platformChargesServiceFee(memberId, profit, orderId, amount);
            // 处理合伙人收益
            memberService.handlePartnerCommission(userChargesResp.isServiceFeeIsSuccess(), memberId, orderId,
                    userChargesResp.getDeductionAward(), userChargesResp.getActualCalcCommissionProfit(), profit);
            // 更新uc_quant利润
            walletService.updateProfit(memberId, orderId, profit);
            // 记录uc_profit_detail收益详情
            profitDetailService.recordDetail(userChargesResp.isServiceFeeIsSuccess(), memberId, quantId, orderId, profit, amount);
            // 统计：增加用户合约收益
            statisticsService.addUserSwapProfit(profit);
        }
    }
}
