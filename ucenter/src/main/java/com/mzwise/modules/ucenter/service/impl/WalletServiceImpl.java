package com.mzwise.modules.ucenter.service.impl;

import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.util.BalanceUtil;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.UnitEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.dto.ScanCodeTransferParam;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import com.mzwise.modules.ucenter.service.WalletService;
import com.mzwise.netty.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import static com.mzwise.constant.SysConstant.SOCKET_REMIND_USER_PREFIX;

/**
 * @Author piao
 * @Date 2021/06/02
 */
@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UcWalletService walletService;
    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void scanCodeTransfer(Long fromMemberId, ScanCodeTransferParam param) {
        Long toMemberId = param.getMemberId();
        BigDecimal amount = param.getAmount();
        String inputPassword = param.getPayPassword();
        UnitEnum unitEnum = param.getUnitEnum();
        String remark = param.getRemark();

        UcMember fromMember = memberService.getMemberById(fromMemberId);
        if (fromMember == null) {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.WalletServiceImpl_001));
        }
        String actualPassword = fromMember.getPayPassword();
        if (StringUtils.isEmpty(actualPassword)) {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.WalletServiceImpl_002));
        }
        if (!passwordEncoder.matches(inputPassword, actualPassword)) {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.WalletServiceImpl_003));
        }
        switch (unitEnum) {
            case USDT:
                UcWallet usdtWallet = walletService.getMasterWallet("USDT", fromMemberId);
                if (usdtWallet.getBalance().compareTo(amount) < 0) {
                    throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.WalletServiceImpl_004));
                }
                BalanceUtil.decreaseBalance(fromMemberId, WalletTypeEnum.QUANT, amount, TransactionTypeEnum.SCAN_CODE_TRANSFER);
                BalanceUtil.increaseBalance(toMemberId, WalletTypeEnum.QUANT, amount, TransactionTypeEnum.SCAN_CODE_TRANSFER);
                break;
            case BTE:
                UcWallet bteWallet = walletService.getMasterWallet("BTE", fromMemberId);
                if (bteWallet.getBalance().compareTo(amount) < 0) {
                    throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.WalletServiceImpl_004));
                }
                BalanceUtil.decreaseBalance(fromMemberId, WalletTypeEnum.PLATFORM, amount, TransactionTypeEnum.SCAN_CODE_TRANSFER);
                BalanceUtil.increaseBalance(toMemberId, WalletTypeEnum.PLATFORM, amount, TransactionTypeEnum.SCAN_CODE_TRANSFER);
                break;
            default:
                throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.WalletServiceImpl_005));
        }

        /* 向收款方发送消息 */
        String msg = "{\"memberId\":" + "\"" + fromMemberId + "\","
                + "\"avatar\":" + "\"" + fromMember.getAvatar() + "\","
                + "\"nickname\":" + "\"" + fromMember.getNickname() + "\","
                + "\"amount\":" + "\"" + amount + "\","
                + "\"unit\":" + "\"" + unitEnum + "\","
                + "\"timestamp\":" + "\"" + new Date() + "\","
                + "\"remark\":" + "\"" + remark + "\"}";
        WebSocketServer.sendMessage(SOCKET_REMIND_USER_PREFIX + toMemberId, msg);
    }
}
