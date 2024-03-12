package com.mzwise.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * settle_type=1  实时结算，流程跟以前一样
 * settle_type=2  且 settled=false     冻结结算模式，且当前策略没有结算； 做上面的 1 2 3
 * settle_type=2  且 settled=true      冻结结算模式，且当前策略已经结算  做：
 *      公司收入增加
 * 	    分配合伙人收益
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfitData {

    /**
     * 用户id
     */
    private String memberId;

    /**
     * 订单ID ,如果是冻结结算 且settled 则这个为null
     */
    private String orderId;

    /**
     * 策略ID
     */
    private int quantId;

    /**
     * 这个策略的第几次轮回，每个轮回的 所有订单 quantIndex 相同 ,如果是2单收益，则这2单的  quantId 和 quantIndex  sides 都相同
     */
  //  private int quantIndex;

    /**
     * 收益 单数，如果是1单，则为1，   如果是2单，则是2
     */
   // private int sides;


    /**
     * 利润 ，有正负
     */
    private String profit;

    /**
     * 交易额 USDT
     */
    private String amount;

    /**
     *策略表中的结算模式
     */
    private Integer settleType;


    /**
     *冻结结算模式下  当前策略 是否已经结算
     */
    private boolean settled;

    /**
     * 冻结模式下分润比例
     */
    private String ratio;
}
