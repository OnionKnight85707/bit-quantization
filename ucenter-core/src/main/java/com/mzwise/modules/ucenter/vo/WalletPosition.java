package com.mzwise.modules.ucenter.vo;

import lombok.Data;

@Data
public class WalletPosition {
    /**
     * 合约币种
     */
    private  String  symbol;

    /**
     * 杠杆倍数
     */
    private int leverage;

    /**
     * 持仓方向  ： 返回值   多， 空
     */
    private  String  positionSide;

    /**
     * 开仓均价
     */
    private  String entryPrice;

    /**
     * 开仓保证金
     */
    private String initialMargin;

    /**
     * 浮动盈亏
     */
    private String unrealizedProfit;

}
