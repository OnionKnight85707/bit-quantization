package com.mzwise.modules.ucenter.service.impl;

import com.mzwise.common.util.BalanceUtil;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.service.UcChargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class UcChargeServiceImpl implements UcChargeService {

    @Override
    public Boolean charge(Long memberId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO)>0) {
            Boolean result = BalanceUtil.decreaseFrozen(memberId, WalletTypeEnum.QUANT_SERVICE, amount, TransactionTypeEnum.CHARGE_QUANT_SERVICE,
                    BigDecimal.ZERO, BigDecimal.ZERO, null);
            return result;
        }
        return true;
    }

}
