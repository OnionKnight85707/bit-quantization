package com.mzwise.common.util;

import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcTransaction;
import com.mzwise.modules.ucenter.mapper.UcTransactionMapper;
import com.mzwise.modules.ucenter.mapper.UcWalletMapper;

import java.math.BigDecimal;

public class BalanceUtil {

    private static UcWalletMapper ucWalletMapper;
    private static UcTransactionMapper ucTransactionMapper;

    static {
        ucWalletMapper = (UcWalletMapper) SpringUtil.getBean("ucWalletMapper");
        ucTransactionMapper = (UcTransactionMapper) SpringUtil.getBean("ucTransactionMapper");
    }

    /**
     * 检查用户余额
     *
     * @param memberId
     * @param type
     * @param amount
     * @return
     */
    public static Boolean checkBalance(Long memberId, WalletTypeEnum type, BigDecimal amount) {
        return ucWalletMapper.checkBalance(memberId, type, amount) > 0;
    }

    /**
     * 检查用户余额
     *
     * @param memberId
     * @param type
     * @param amount
     * @return
     */
    public static Boolean checkFrozen(Long memberId, WalletTypeEnum type, BigDecimal amount) {
        return ucWalletMapper.checkFrozen(memberId, type, amount) > 0;
    }

    /**
     * 增加用户余额(并记录资金流水)
     *
     * @param memberId            用户id
     * @param accountType         真实虚拟
     * @param amount              金额>0
     * @param transactionTypeEnum 类型
     * @return
     */
    public static Boolean increaseBalance(Long memberId, WalletTypeEnum accountType, BigDecimal amount, TransactionTypeEnum transactionTypeEnum) {
        return increaseBalance(memberId, accountType, amount, BigDecimal.ZERO, transactionTypeEnum, BigDecimal.ZERO, BigDecimal.ZERO, null);
    }

    /**
     * 增加用户余额(并记录资金流水)
     * @param memberId 用户id
     * @param accountType 钱包类型
     * @param amount 充值金额
     * @param fee 充值手续费
     * @param transactionTypeEnum 交易类型
     * @param amountBeforeChange 充值前金额
     * @param amountAfterChange 充值后金额
     * @param remark 备注
     * @return
     */
    public static Boolean increaseBalance(Long memberId, WalletTypeEnum accountType, BigDecimal amount, BigDecimal fee,
            TransactionTypeEnum transactionTypeEnum, BigDecimal amountBeforeChange, BigDecimal amountAfterChange, String remark) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("金额必须为正");
        }
        boolean b = ucWalletMapper.increaseBalance(memberId, accountType, amount) > 0;
        if (b) {
            addTransaction(memberId, amount, fee, accountType, transactionTypeEnum, amountBeforeChange, amountAfterChange, remark);
        }
        return b;
    }

    /**
     * 减少用户余额
     *
     * @param accountType         真实虚拟
     * @param amount              金额>0
     * @param transactionTypeEnum 类型
     * @return
     */
    public static Boolean decreaseBalance(Long memberId, WalletTypeEnum accountType, BigDecimal amount, TransactionTypeEnum transactionTypeEnum) {
        return decreaseBalance(memberId, accountType, amount, BigDecimal.ZERO, transactionTypeEnum);
    }

    public static Boolean decreaseBalance(Long memberId, WalletTypeEnum accountType, BigDecimal amount, BigDecimal fee, TransactionTypeEnum transactionTypeEnum) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("金额必须为正");
        }
        boolean b = ucWalletMapper.decreaseBalance(memberId, accountType, amount) > 0;
        if (b) {
            // 记账
            addTransaction(memberId, amount.negate(), fee, accountType, transactionTypeEnum, BigDecimal.ZERO, BigDecimal.ZERO, null);
        }
        return b;
    }

//    /**
//     * 减少用户余额
//     *
//     * @param accountType         真实虚拟
//     * @param amount              金额>0
//     * @param transactionTypeEnum 类型
//     * @return
//     */
//    public static Boolean withdrawalBalance(Long memberId, WalletTypeEnum accountType, BigDecimal amount, TransactionTypeEnum transactionTypeEnum) {
//        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
//            throw new RuntimeException("金额必须为正");
//        }
//        // 记账
//        if (accountType.equals(WalletTypeEnum.REAL)) {
//            addTransaction(memberId, amount.negate(), transactionTypeEnum, GeneralCheckStatusEnum.APPLYING);
//        }
//        return ucWalletMapper.decreaseBalance(memberId, accountType, amount) > 0;
//    }

    /**
     * 冻结用户余额
     * @param memberId 用户id
     * @param walletType 钱包类型
     * @param amount 需要冻结的金额
     * @param transactionTypeEnum 交易类型
     * @param remark 备注
     * @return
     */
    public static Boolean freezeBalance(Long memberId, WalletTypeEnum walletType, BigDecimal amount, TransactionTypeEnum transactionTypeEnum, String remark) {
        return freezeBalance(memberId, walletType, amount, BigDecimal.ZERO, transactionTypeEnum, BigDecimal.ZERO, BigDecimal.ZERO, remark);
    }

    /**
     * 冻结用户余额
     * @param memberId 用户id
     * @param walletType 钱包类型
     * @param amount 需要冻结的金额
     * @param fee 手续费
     * @param transactionTypeEnum 交易类型
     * @param beforeAmount 交易前余额
     * @param afterAmount 交易后余额
     * @param remark 备注
     * @return
     */
    public static Boolean freezeBalance(Long memberId, WalletTypeEnum walletType, BigDecimal amount, BigDecimal fee, TransactionTypeEnum transactionTypeEnum,
                                        BigDecimal beforeAmount, BigDecimal afterAmount, String remark) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("金额必须为正");
        }
        boolean b = ucWalletMapper.freezeBalance(memberId, walletType, amount) > 0;
        if (b) {
            // 记账
            addTransaction(memberId, amount.negate(), fee, walletType, transactionTypeEnum, beforeAmount, afterAmount, remark);
        }
        return b;
    }

    /**
     * 解冻用户余额
     *
     * @param accountType         真实虚拟
     * @param amount              金额>0
     * @param transactionTypeEnum 类型
     * @return
     */
    public static Boolean thawBalance(Long memberId, WalletTypeEnum accountType, BigDecimal amount, TransactionTypeEnum transactionTypeEnum) {
        return thawBalance(memberId, accountType, amount, BigDecimal.ZERO, transactionTypeEnum, BigDecimal.ZERO, BigDecimal.ZERO, null);
    }

    /**
     * 解冻用户余额
     * @param memberId 用户id
     * @param walletType 钱包类型
     * @param amount 需要解冻的金额
     * @param fee 手续费
     * @param transactionTypeEnum 交易类型
     * @param beforeAmount 交易前余额
     * @param afterAmount 交易后余额
     * @param remark 备注
     * @return
     */
    public static Boolean thawBalance(Long memberId, WalletTypeEnum walletType, BigDecimal amount, BigDecimal fee, TransactionTypeEnum transactionTypeEnum,
                                      BigDecimal beforeAmount, BigDecimal afterAmount, String remark) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("金额必须为正");
        }
        boolean b = ucWalletMapper.thawBalance(memberId, walletType, amount) > 0;
        if (b) {
            // 记账
            addTransaction(memberId, amount, fee, walletType, transactionTypeEnum, beforeAmount, afterAmount, remark);
        }
        return b;
    }

    /**
     * 减少用户冻结金额
     * @param memberId 用户id
     * @param walletType 钱包类型
     * @param amount 需要减少的冻结金额
     * @param transactionTypeEnum 交易类型
     * @param beforeAmount 交易前余额
     * @param afterAmount 交易后余额
     * @param remark 备注
     * @return
     */
    public static Boolean decreaseFrozen(Long memberId, WalletTypeEnum walletType, BigDecimal amount, TransactionTypeEnum transactionTypeEnum,
                                         BigDecimal beforeAmount, BigDecimal afterAmount, String remark) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("金额必须为正");
        }
        boolean b = ucWalletMapper.decreaseFrozen(memberId, walletType, amount) > 0;
        if (b) {
            // 记账
            addTransaction(memberId, amount.negate(), BigDecimal.ZERO, walletType, transactionTypeEnum, beforeAmount, afterAmount, remark);
        }
        return b;
    }

    /**
     * 记录资金明细
     * @param memberId 会员id
     * @param amount 金额
     * @param fee 手续费
     * @param walletTypeEnum 钱包类型
     * @param transactionTypeEnum 交易类型
     * @param amountBeforeChange 变动前金额
     * @param amountAfterChange 变动后金额
     * @param remark 备注
     */
    private static void addTransaction(Long memberId, BigDecimal amount, BigDecimal fee, WalletTypeEnum walletTypeEnum,
            TransactionTypeEnum transactionTypeEnum, BigDecimal amountBeforeChange, BigDecimal amountAfterChange, String remark) {
        UcTransaction transaction = new UcTransaction();
        transaction.setMemberId(memberId);
        transaction.setAmount(amount);
        transaction.setFee(fee);
        transaction.setWalletType(walletTypeEnum);
        transaction.setType(transactionTypeEnum);
        transaction.setAmountBeforeChange(amountBeforeChange);
        transaction.setAmountAfterChange(amountAfterChange);
        transaction.setRemark(remark);
        ucTransactionMapper.insert(transaction);
    }
}
