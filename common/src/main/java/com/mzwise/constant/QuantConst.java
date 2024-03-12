package com.mzwise.constant;

/**
 * 系统常量
 *
 * @author wmf
 * @date 2017年12月18日
 */
public class QuantConst {

    /**
     * 系统设置reids
     */

    public static final String REDIS_KEY_QUANT_SETTING = "QUANT_SETTING";


    /**
     * 对冲策略 最大下单数量
     */
    public static final int FUTURE_MAX_ORDERS=6;


    /**
     * 对冲策略 下了平仓单后 超时不成交 就撤单。
     */
    public static final int CLOSING_WAITING_MIN=5;


    /**
     * 对冲策略 已经使用了 最大保证金数目
     */
    public static final int MAX_MARGIN=1000;


    /**
     * macd 金叉或者死叉多久内 可以开仓
     */
    public static final int MACD_OPEN_DIST_MAX=6;


    /**
     * 判断 macd  在dist <=8 内  金叉或者死叉
     */
    public static int  jinchaSichaMaxDist=8;

    /**
     * 最大补仓次数 为 2次，每次跟初次下发相同。 每次多200
     * 500  700  900
     */
    public static final int maxAddTimes=2;

    /**
     * 补仓时 每次币上次 多 200u
     */
    public  static int  perAdd=200;


    /**
     * 价格 亏损了多少就 补仓 ，默认 4%
     */
    public static float addPriceDif=3f;


    /**
     *  MACD 止损价格百分比
     */
    public static final  float  MACD_STOP_LOSS_PERCENT=3.0f;


    /**
     * MACD 止盈百分比
     */
    public static final  float  MACD_STOP_PROFIT_PERCENT=6.0f;


    /**
     * macd  追踪止盈 里面 当追踪到  价格涨幅多少百分比时 开始监控 回落
     */
    public static final float  MACD_TRACE_MAX_PERCENT=3f;

    /**
     * macd  追踪止盈 ：当价格回落了原来最高涨幅的多少 就止盈  0.5 表示回落了 50%
     */
    public static final float  MACD_TRACE_BACK_PERCENT=0.5f;



    public static  final int MAX_COVER_TIMES=10;


}
