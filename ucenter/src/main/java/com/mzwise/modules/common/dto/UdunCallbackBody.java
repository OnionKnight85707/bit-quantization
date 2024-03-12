package com.mzwise.modules.common.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UdunCallbackBody {

    /**
     * 交易数量
     */
    private String amount;

    /**
     * 交易地址
     */
    private String address;

    /**
     * 区块链交易哈希
     */
    private String txId;

    /**
     * 矿工费
     */
    private String fee;

    /**
     * 币种精度
     */
    private String decimals;

    /**
     * 子币种编号
     */
    private String coinType;

    /**
     * 主币种编号
     */
    private String mainCoinType;

    /**
     * 业务编号  充币则无值
     */
    private String businessId;

    /**
     * 区块高度
     */
    private String blockHigh;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 业务流水号
     */
    private String tradeId;

    /**
     * 交易类型
     */
    private Integer tradeType;

    /**
     * 备注
     */
    private String memo;

}
