package com.mzwise.modules.ucenter.vo;

import lombok.Data;

import java.util.List;

@Data
public class AccountAssetResponse {
    /**
     * 钱包总余额
     */
    private String walletBalance;

    /**
     * 钱包可用
     */
    private String available ;

    /**
     * 钱包冻结
     */
    private String frozen ;


    /**
     * 目前持仓情况
     */
    private List<WalletPosition> positions;


}
