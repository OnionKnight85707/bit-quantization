package com.mzwise.modules.ucenter.service;

import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.vo.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-30
 */
public interface UcWalletService extends IService<UcWallet> {
    /**
     * 给用户分配初始系统账户
     *
     * @param memberId
     */
    void initWallets(Long memberId);

    /**
     * 获取一个用户所有账户及其对应的余额
     *
     * @param memberId
     * @return
     */
    BalanceAllTypeVO getAllWalletsBalance(Long memberId);

    /**
     * 获取一个用户所有资产总和(转换为USDT和CNY)
     *
     * @param memberId
     * @return
     */
    BalanceVO getTotalBalance(Long memberId);

    /**
     * 获取一个用户某账户所有资产总和(转换为USDT和CNY)
     *
     * @param memberId
     * @return
     */
    BalanceVO getTotalBalance(Long memberId, WalletTypeEnum type);

    /**
     * 兑换可选项账户
     *
     * @param memberId
     */
    List<ExchangeOptionVO> exchangeOptions(Long memberId);

    /**
     * 获得某一类型的账户
     *
     * @param memberId
     * @param type
     * @return
     */
    UcWallet getWallet(Long memberId, WalletTypeEnum type);

    /**
     * 获取主账户
     *
     * @param memberId
     * @return
     */
    List<UcWallet> getMasterWallets(Long memberId);

    /**
     * 获取某一币种主账户
     *
     * @param memberId
     * @return
     */
    UcWallet getMasterWallet(String symbol, Long memberId);

    /**
     * 获取用户的所有账户(返回实体)
     *
     * @param memberId
     * @return
     */
    WalletAllTypeVO getWalletsEntity(Long memberId);

    /**
     * 获取用户的所有账户(返回map)
     *
     * @param memberId
     * @return
     */
    HashMap<String, UcWallet> getWalletsMap(Long memberId);

    /**
     * 划转账户可选项
     *
     * @param memberId
     */
    List<TransferOptionVO> transferOptions(Long memberId, String symbol);

    /**
     * 划转
     *
     * @param memberId
     * @param from
     * @param to
     * @param amount
     */
    void transfer(Long memberId, WalletTypeEnum from, WalletTypeEnum to, BigDecimal amount);

    /**
     * 兑换
     *
     * @param memberId
     * @param from
     * @param to
     * @param amount
     */
    void exchange(Long memberId, WalletTypeEnum from, WalletTypeEnum to, BigDecimal amount);

    /**
     * 通过币种和充币地址查询钱包
     *
     * @param symbol
     * @param address
     * @return
     */
    UcWallet getBySymbolAndAddress(String symbol, String address);

    /**
     * 充币地址查询钱包
     * @param address
     * @return
     */
    UcWallet getByAddress(String address);

    /**
     * 增加锁仓服务费
     *
     * @param memberId
     * @param amount
     */
    void freezeService(Long memberId, BigDecimal amount);

    /**
     * 获取平台服务费比例
     * @return
     */
    BigDecimal getPlatformServiceRate();

    /**
     * 平台收取服务费
     * @param memberId 用户id
     * @param profit 利润
     * @param orderId 订单id
     * @param amount 金额
     * @return 服务费是否收取成功：true：成功， false：失败
     */
    UserChargesResp platformChargesServiceFee(Long memberId, BigDecimal profit, String orderId, BigDecimal amount);

    /**
     * 用户扣费
     * @param wallet 钱包
     * @param amount 需要扣费的金额
     * @param profit 收益
     * @return
     */
    UserChargesResp userCharges(UcWallet wallet, BigDecimal amount, BigDecimal profit);

    /**
     * 减少余额
     * @param walletId 钱包id
     * @param amount 金额
     */
    void subtractBalance(Long walletId, BigDecimal amount);

    /**
     * 增加余额
     * @param walletId 钱包id
     * @param amount 金额
     */
    void addBalance(Long walletId, BigDecimal amount);

    /**
     * 平台收费
     * @param amount 金额
     */
    void platformCharges(BigDecimal amount);

    /**
     * 平台扣费
     * @param amount 金额
     */
    void platformDeduction(BigDecimal amount);

    /**
     * 更新利润
     * @param memberId 用户id
     * @param orderId 订单id
     * @param profit 利润
     */
    void updateProfit(Long memberId, String orderId, BigDecimal profit);

    /**
     * 手动增加余额
     * @param memberId
     * @param amount
     */
    void manualAddBalance(Long memberId, BigDecimal amount);

    /**
     * 手动减少余额
     * @param memberId
     * @param amount
     */
    void manualSubtractBalance(Long memberId, BigDecimal amount);

    /**
     *  获取某个用户的奖励金额
     * @param memberId
     * @param type
     * @return
     */
    BalanceVO getAwardBalance(Long memberId,WalletTypeEnum type);

    /**
     *   冻结用户服务费
     * @param memberId
     * @param type
     * @param amount
     */
    void freeze(Long memberId,WalletTypeEnum type,BigDecimal amount);

    void decreaseFrozen(Long memberId,WalletTypeEnum type,BigDecimal amount);

    /**
     * 判断服务费
     * @param memberId
     * @return
     */
    void checkMoneyExist(Long memberId);

}
