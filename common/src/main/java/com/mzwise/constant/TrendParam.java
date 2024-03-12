package com.mzwise.constant;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 *  料子：  trend,1,0.03,0.052,0.021,0.082,5,0.02_0.02_0.02_0.02_0.02,0.02_0.02_0.02_0.02_0.02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrendParam {

   //为了与其他技术指标区别
   public static String UNIFIED ="unified";

   private String typeName= UNIFIED;

   /**
    * 首单 方式：  1 按照百分比   2按照金额USDT
    */
   private FirstTypeEnum firstType;

   /**
    * 首单值，如果是百分比，比如 3% ，就存 0.03
    */
   private BigDecimal firstValue;

   /**
    * "开平方式： 1按照外部信号， 2按照追踪止盈"
    */

   private Integer openCloseMode;


   /**
    * 止盈比例  0.05 表示 5% ,包含杠杆
    */
   private BigDecimal stopProfitPercent;


   /**
    * 止盈回调比例 ，比如 值为0.02% 表示最开始赚了5%，回调了2% ，现在赚3% 就要平仓了
    */
   private BigDecimal backPercent;



   /**
    * 补仓次数  加仓层数
    */
   private Integer coverTimes;

   /**
    * 补仓金额百分比参数： 格式 0.02_0.02_0.02_0.02_0.02
    */
   private String coverAmountPercent;

   /**
    * 补仓间隔 百分比 用下横线分开 比如  0.02_0.02_0.02_0.02_0.02
    */
   private String coverInterval;

   public  enum  CoverParamType {
      AMOUNT,PRICE;
   }


}
