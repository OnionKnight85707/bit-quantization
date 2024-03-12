package com.mzwise.modules.ucenter.mapper;

import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-03-30
 */
public interface UcWalletMapper extends BaseMapper<UcWallet> {
    /**
     * 检查用户余额
     * @param memberId
     * @param type
     * @param amount
     * @return
     */
    int checkBalance(Long memberId, WalletTypeEnum type, BigDecimal amount);


    /**
     * 检查用户冻结余额
     * @param memberId
     * @param type
     * @param amount
     * @return
     */
    int checkFrozen(Long memberId, WalletTypeEnum type, BigDecimal amount);

    /**
     * 增加用户余额
     * @param memberId
     * @param type
     * @param amount
     * @return
     */
    int increaseBalance(Long memberId, WalletTypeEnum type, BigDecimal amount);

    /**
     * 减少用户余额
     * @param memberId
     * @param type
     * @param amount
     * @return
     */
    int decreaseBalance(Long memberId, WalletTypeEnum type, BigDecimal amount);

    /**
     * 冻结用户余额
     * @param memberId
     * @param type
     * @param amount
     * @return
     */
    int freezeBalance(Long memberId, WalletTypeEnum type, BigDecimal amount);

    /**
     * 解冻用户余额
     * @param memberId
     * @param type
     * @param amount
     * @return
     */
    int thawBalance(Long memberId, WalletTypeEnum type, BigDecimal amount);

    /**
     * 减少用户冻结余额
     * @param memberId
     * @param type
     * @param amount
     * @return
     */
    int decreaseFrozen(Long memberId, WalletTypeEnum type, BigDecimal amount);

    /**
     * 增加钱包收益
     * @param memberId
     * @param type
     * @param amount
     * @return
     */
    int addProfit(Long memberId, WalletTypeEnum type, BigDecimal amount);

    /**
     * 设置钱包收益
     * @param memberId
     * @param type
     * @param todayProfit
     * @param totalProfit
     * @return
     */
    int setProfit(Long memberId, WalletTypeEnum type, BigDecimal todayProfit, BigDecimal totalProfit);

    int resetTodayProfit();

    /**
     * 获取平台服务费比例
     * @return
     */
//    @Cacheable(key = "#chargeServiceRate")
    BigDecimal getPlatformServiceRate();

    /**
     * 减少奖励金额
     * @param walletId 钱包id
     * @param amount 金额
     */
    void subtractTicket(@Param("walletId") Long walletId, @Param("amount") BigDecimal amount);

    /**
     * 减少余额
     * @param walletId 钱包id
     * @param amount 金额
     */
    void subtractBalance(@Param("walletId") Long walletId, @Param("amount") BigDecimal amount);

    /**
     * 增加余额
     * @param walletId 钱包id
     * @param amount 金额
     */
    void addBalance(@Param("walletId") Long walletId, @Param("amount") BigDecimal amount);

    /**
     * 平台收费
     * @param amount 金额
     */
    void platformCharges(@Param("amount") BigDecimal amount);

    /**
     * 平台扣费
     * @param amount 金额
     */
    void platformDeduction(@Param("amount") BigDecimal amount);

    /**
     * 更新钱包利润
     * @param memberId 用户id
     * @param profit 利润
     */
    void updateWalletProfit(@Param("memberId") Long memberId, @Param("profit") BigDecimal profit);

    /**
     * 根据订单id查询交易类型
     * @param orderId 订单id
     * @return 交易类型
     */
    Integer selTradeTypeByOrderId(@Param("orderId") String orderId);

    /**
     * 更新合约收益
     * @param memberId 用户id
     * @param profit 利润
     */
    void updateSwapProfit(@Param("memberId") Long memberId, @Param("profit") BigDecimal profit);

    /**
     * 更新现货收益
     * @param memberId 用户id
     * @param profit 利润
     */
    void updateExchangeProfit(@Param("memberId") Long memberId, @Param("profit") BigDecimal profit);

}
