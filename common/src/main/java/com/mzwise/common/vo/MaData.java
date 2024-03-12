package com.mzwise.common.vo;

import lombok.Data;

/**
 *  某X分钟 的数据  （比如 5分钟  15分钟 等）
 */
@Data
public class MaData {

    private  boolean  ma60Up=false;
    private  boolean  ma20Up=false;
    private  boolean  ma10Up=false;
    private  boolean  ma5Up=false;

    private String ma60Price;
    private String ma20Price;
    private String ma10Price;
    private String ma5Price;


    private float last1Percent;

    private float last2Percent;

    private float last3Percent ;

    private float last4Percent;

    private float last5Percent ;

    /**
     * 多少分钟 基本的K线
     */
    private int min;

    /**
     * macd 是否金叉， true表示金叉 ，false表示 死叉
     */
    private boolean macdJincha;

    /**
     * 距离金叉 或者 死叉的 K线周期 数目
     */
    private int  dist;


    /**
     * 金叉或者死叉时 前面有几根 相反的K线，至少要3根
     * 比如  macd值：
     * -3 -2  -1  1  2 3     上面标识1的表示开始金叉，金叉前有 3根相反方向的   unDist=3
     *
     *    -2  0.2  -1  1  2 3    上面表示1的开始金叉，前面有2根 相反方向的
     *    作用：避免反复震荡，
     *    使用方式，如果unDist<=3,则开仓和平仓 要求 dist>=2+3 =5
     */
    private int unDist;




    /**
     * 20 线 连续 在60上面 多少根K线
     */

    private int ava20AboveAva60Times;


    /**
     * 20 线 连续 在60下面 多少根K线
     */
    private int ava20belowAva60Times;

    /**
     * 追踪止盈技术：
     *每个定时任务记录 盈利最大值，如果盈利最大值超过某个百分比 （价格涨幅，与杠杆无关，比如 2%）,则如果回吐超过40% 就止盈
     *  比如最高赚了2% ，现在跌到 1.2% 就 止盈
     */


    /**
     *  dist=8 内死叉 且 60>20
     */

    private boolean  sichaAnd60Greater20=false;

    /**
     *  dist=8 内金叉 且 20>60
     */

    private boolean  jinchaAnd20Greater60=false;


    /**
     * 金叉或者死叉时间
     */
    private Long crossTime=0L;

}
