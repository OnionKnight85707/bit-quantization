package com.mzwise.constant;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FutureParam {
   //为了与其他技术指标区别，本对冲策略第1个参数为 future
   public static String FUTURE="future";

   private String typeName=FUTURE;
   private int ma;
   private int initUsdt;
   private int perAddUsdt;
   private BigDecimal priceDif;
   private BigDecimal stopProfit;
   private BigDecimal stopLoss;

   /**
    * 倍投 参数默认为1
    */
   private int mul;

//   public static FutureParam fromJson(String json)
//   {
//      return JSON.parseObject(json,FutureParam.class);
//   }

}
