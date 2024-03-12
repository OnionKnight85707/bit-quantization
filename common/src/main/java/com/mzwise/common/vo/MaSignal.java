package com.mzwise.common.vo;



import com.mzwise.common.vo.macd.MacdOutQuota;
import com.mzwise.constant.QuantConst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.misc.Signal;


import java.math.BigDecimal;


/**
 * 1，    60分钟死叉 且 60>20
 * 2,    30分钟死叉 且 60>20 ,且60分钟 60>20
 * 3,    15分钟死叉 且 60>20 ,且30分钟 60>20
 * 4,    5分钟死叉 且 60>20 ,且15分钟 60>20
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaSignal  {

    private String symbol;

    private String  price;
    /**
     * 信号有效期(毫秒)
     */
    private Long exp;

    private MaData min1;
    private MaData min5;
    private  MaData  min15;
    private  MaData  min30;
    private  MaData  min60;

    public MaData getMaByPeriod(int min)
    {
        if (min==1)
        {
            return min1;
        }
        if (min==5)
        {
            return min5;
        }
        if (min==15)
        {
            return min15;
        }
        if (min==30)
        {
            return min30;
        }
        if (min==60)
        {
            return min60;
        }
        return null;
    }


    public boolean isKaiKouUp(int min)
    {
        MaData data=getMaData(min);

        if (        Float.parseFloat(data.getMa5Price())>Float.parseFloat(data.getMa10Price() )
                &&  Float.parseFloat(data.getMa10Price())>Float.parseFloat(data.getMa20Price() )
                &&  Float.parseFloat(data.getMa20Price())>Float.parseFloat(data.getMa60Price() )
        )
        {
            return true;
        }
        return  false;
    }

    public MaData getMaData(int min)
    {
        MaData data=min5;
        if(min==15)
        {
            data=min15;
        }else if(min==30)
        {
            data=min30;
        }else if(min==60)
        {
            data=min60;
        }else
        {
            throw  new  IllegalArgumentException("非法的周期 数目");
        }
        return  data;
    }
    public boolean isKaiKouDown(int min)
    {
        MaData data=getMaData(min);


        if (        Float.parseFloat(data.getMa5Price())<Float.parseFloat(data.getMa10Price() )
                &&  Float.parseFloat(data.getMa10Price())<Float.parseFloat(data.getMa20Price() )
                &&  Float.parseFloat(data.getMa20Price())<Float.parseFloat(data.getMa60Price() )
        )
        {
            return true;
        }
        return  false;
    }

    /**
     * 判断是否向上突破
     *       判断最后几根K线 是否 涨幅百分比之和大于1
     * @param min
     * @return
     */
    public boolean  tupoUp(int min)
    {
        float max=1.0f;
        MaData data=getMaData(min);
        if (       data.getLast1Percent()>max
                || data.getLast1Percent()+data.getLast2Percent()>max
                || data.getLast1Percent()+data.getLast2Percent()+data.getLast3Percent()>max
                || data.getLast1Percent()+data.getLast2Percent()+data.getLast3Percent()+ data.getLast4Percent()>max
                || data.getLast1Percent()+data.getLast2Percent()+data.getLast3Percent()+ data.getLast4Percent()+data.getLast5Percent()>max
        )
        {
            return true;
        }
        return  false;
    }

    public boolean canCloseLong() {

        if (min60.isMa60Up())
        {
            if (min15.getLast1Percent() + min15.getLast2Percent() + min15.getLast3Percent() > 0) {
                return true;
            }

            return  false;

        }else
        {
            return true;
        }

    }

    public boolean canCloseShort() {

        if (min60.isMa60Up())
        {
            return  true;
        }else
        {
            if (min15.getLast1Percent() + min15.getLast2Percent() + min15.getLast3Percent()< 0) {
                return true;
            }
            return false;
        }

    }

    /**
     *  能不能做多 （min60 线 60大于20 连续30根以上）
     * @return
     */
    public boolean canOpenLong()
    {

        if (  min15.getLast1Percent()+min15.getLast2Percent()+min15.getLast3Percent()>0.1)
        {
            return false;
        }

        float difmin15_60= (Float.parseFloat(price)-Float.parseFloat(min15.getMa60Price()))/Float.parseFloat(price)*100;


        if (difmin15_60>2)
        {
            return false;
        }


        //最近2-3根 涨幅要小于0.5
        if (!min30.isMa60Up())
        {


        }

        //如果 min30  20>60  且 长期 死叉，则不开多

        if (    difmin15_60>1
                && Float.parseFloat(min30.getMa20Price())>Float.parseFloat(min30.getMa60Price())
                && Float.parseFloat(min15.getMa20Price())>Float.parseFloat(min15.getMa60Price() )
                && !min30.isMacdJincha() && min30.getDist()>=5)
        {
            return  false;
        }


        //如果  长期下跌趋势，60分钟周期  60线到 20线距离 7个点以上，且15分钟  20与60线距离1%内，则macd 金叉了可以抄底做多
       float dif60= new BigDecimal(min60.getMa60Price()).subtract(new BigDecimal(price)).divide(new BigDecimal(min60.getMa60Price()),4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal("100")).floatValue();
       float difmin15_2060= new BigDecimal(min15.getMa60Price()).subtract(new BigDecimal(min15.getMa20Price()))
                .divide(new BigDecimal(min15.getMa60Price()),4,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal("100")).floatValue();

        if (dif60>6 && Math.abs(difmin15_2060)<1)
           return true;

        //如果 60分钟周期  20线在60线下方30根K线以上 大空头方向，什么情况可以抄底做多？
        if ( min60.getAva20belowAva60Times()>30)
        {

            //todo: 这种情况 下 什么时候可以做多
            return false;

        }

        float min15Dif60= (Float.parseFloat(price)-Float.parseFloat(min15.getMa60Price()))/Float.parseFloat(price)*100;
        float min30Dif60= (Float.parseFloat(price)-Float.parseFloat(min30.getMa60Price()))/Float.parseFloat(price)*100;
        float min30Dif2060= (Float.parseFloat(min30.getMa20Price())-Float.parseFloat(min30.getMa60Price()))/Float.parseFloat(price)*100;
        float min60Dif2060= (Float.parseFloat(min60.getMa20Price())-Float.parseFloat(min60.getMa60Price()))/Float.parseFloat(price)*100;

        if (    Float.parseFloat(min60.getMa20Price())>Float.parseFloat(min60.getMa60Price()) && min60.isMa60Up()
                && Float.parseFloat(min30.getMa20Price())>Float.parseFloat(min30.getMa60Price())
        )
        {
            //如果多头行情下面，5分钟要 开口
            if (       Float.parseFloat(min5.getMa5Price())>Float.parseFloat(min5.getMa10Price() )
                    && Float.parseFloat(min5.getMa10Price())>Float.parseFloat(min5.getMa20Price() )
            )
            {
                return true;
            }else
            {
                return false;
            }

        }



        //其他情况 ，可以做多
        return  true;

    }

    /**
     * 能不能做空 （是否处于极度 多头的 情况）
     * @return
     */
    public boolean canOpenShort()
    {

        //最近2-3根 跌幅 不要超过0.5
        if (min15.getLast1Percent()+min15.getLast2Percent()+min15.getLast3Percent()<0)
        {
            return false;
        }


        //在多头行情下 ，30分钟 macd 持续 死叉 ，这个时候可以做空
        float min15Dif60= (Float.parseFloat(price)-Float.parseFloat(min15.getMa60Price()))/Float.parseFloat(price)*100;
        float min30Dif60= (Float.parseFloat(price)-Float.parseFloat(min30.getMa60Price()))/Float.parseFloat(price)*100;
        float min30Dif2060= (Float.parseFloat(min30.getMa20Price())-Float.parseFloat(min30.getMa60Price()))/Float.parseFloat(price)*100;
        float min60Dif2060= (Float.parseFloat(min60.getMa20Price())-Float.parseFloat(min60.getMa60Price()))/Float.parseFloat(price)*100;
        float min60NewDif60= (Float.parseFloat(price)-Float.parseFloat(min60.getMa60Price()))/Float.parseFloat(price)*100;

        if (    Float.parseFloat(min60.getMa20Price())>Float.parseFloat(min60.getMa60Price()) && min60.isMa60Up()
             && Float.parseFloat(min30.getMa20Price())>Float.parseFloat(min30.getMa60Price())
        )
        {
            if (min60.getAva20AboveAva60Times()<30)
            {
                return true;
            }
            if (min60Dif2060<1 && min60.isMa20Up())
            {
                return false;
            }
            if (min30Dif2060<1)
            {
                return false;
            }
            if (Float.parseFloat(min15.getMa20Price())>Float.parseFloat(min15.getMa60Price() ) && min15Dif60<1 )
            {
                return false;
            }

            if (min15Dif60 >1.4 && !min30.isMacdJincha() && min30.getDist()>=5  && Float.parseFloat(min30.getMa20Price())>Float.parseFloat(min30.getMa60Price()))
            {
                return true;
            }

            return false;

        }
        //如果min60  20>60 且60向上，且 newdif60<1，且2060dif <1 ,则不能做空
        if (min60.isMa60Up() && Float.parseFloat(min60.getMa20Price())>Float.parseFloat(min60.getMa60Price())
             && min60NewDif60<1 && min60Dif2060>1 )
        {
             return false;
        }

        return  true;

    }

    /**
     * 判断是否 死叉 做空
     *
     *  * 1，    60分钟死叉 且 60>20
     *  * 2,    30分钟死叉 且 60>20 ,且60分钟 60>20
     *  * 3,    15分钟死叉 且 60>20 ,且30分钟 60>20
     *  * 4,    5分钟死叉 且 60>20 ,且15分钟 60>20
     *
     * @return
     */
    public boolean canSichaDoShort()
    {
        if (        min60.isSichaAnd60Greater20()
                ||  min30.isSichaAnd60Greater20() && Float.parseFloat(min60.getMa60Price())>Float.parseFloat(min60.getMa20Price())
                ||  min15.isSichaAnd60Greater20() && Float.parseFloat(min30.getMa60Price())>Float.parseFloat(min30.getMa20Price())
                ||  min5.isSichaAnd60Greater20() && Float.parseFloat(min15.getMa60Price())>Float.parseFloat(min15.getMa20Price())

        )
        {
            return true;
        }
        return  false;
    }


    public boolean canJinchaDoLong()
    {
        if (        min60.isJinchaAnd20Greater60()
                ||  min30.isJinchaAnd20Greater60() && Float.parseFloat(min60.getMa20Price())>Float.parseFloat(min60.getMa60Price())
                ||  min15.isJinchaAnd20Greater60() && Float.parseFloat(min30.getMa20Price())>Float.parseFloat(min30.getMa60Price())
                ||  min5.isJinchaAnd20Greater60() && Float.parseFloat(min15.getMa20Price())>Float.parseFloat(min15.getMa60Price())

        )
        {
            return true;
        }
        return  false;
    }


    public static MaData parse(SymbolKline sk)
    {
        if (sk==null)
            return null;
        MaData result=new MaData();
        MaItem ma5Item= TaLibUtil.parseMa(sk.getKLines(),5);
        MaItem ma10Item= TaLibUtil.parseMa(sk.getKLines(),10);
        MaItem ma20Item= TaLibUtil.parseMa(sk.getKLines(),20);
        MaItem ma60Item= TaLibUtil.parseMa(sk.getKLines(),60);

        result.setMa5Price(""+ma5Item.getMaPrice());
        result.setMa10Price(""+ma10Item.getMaPrice());
        result.setMa20Price(""+ma20Item.getMaPrice());
        result.setMa60Price(""+ma60Item.getMaPrice());

        result.setMa5Up(ma5Item.isUp());
        result.setMa10Up(ma10Item.isUp());
        result.setMa20Up(ma20Item.isUp());
        result.setMa60Up(ma60Item.isUp());

        /**
         * 最近几根K线的涨幅 ，与均线 数目无关，一起返回
         */
        result.setLast1Percent(ma5Item.getLast1Percent());
        result.setLast2Percent(ma5Item.getLast2Percent());
        result.setLast3Percent(ma5Item.getLast3Percent());
        result.setLast4Percent(ma5Item.getLast4Percent());
        result.setLast5Percent(ma5Item.getLast5Percent());

        MacdOutQuota quota = TaLibUtil.macd(sk.getKLines(), 12, 26, 9);

//        log.info(" macd : macd ={} {} {} {} {} {}  ",quota.getMacd()[quota.getMacd().length-1],quota.getMacd()[quota.getMacd().length-2],quota.getMacd()[quota.getMacd().length-3],quota.getMacd()[quota.getMacd().length-4]
//                ,quota.getMacd()[quota.getMacd().length-5],quota.getMacd()[quota.getMacd().length-6]);

        if (quota.getMacd()[quota.getMacd().length-2]>=0)
        {
            result.setMacdJincha(true);
            for (int j=1;j<=60;j++)
            {
                if (quota.getMacd()[quota.getMacd().length-1-j]<0)
                {
                    result.setDist(j);
                    result.setCrossTime(sk.getKLines().get(sk.getKLines().size()-j).getTime());
                    break;
                }else
                {
                    result.setCrossTime(sk.getKLines().get(sk.getKLines().size()-j).getTime());
                }
            }

            //统计unDisk 数目
            int unDiskTotal=0;
            for (int j=1;j<=6;j++)
            {
                if (quota.getMacd()[quota.getMacd().length-1-j]<0)
                {
                    unDiskTotal++;
                }
            }
            result.setUnDist(unDiskTotal);

        }else
        {
            for (int j=1;j<=60;j++)
            {
                if (quota.getMacd()[quota.getMacd().length-1-j]>0)
                {
                    result.setDist(j);
                    result.setCrossTime(sk.getKLines().get(sk.getKLines().size()-j).getTime());
                    break;
                }else
                {
                    result.setCrossTime(sk.getKLines().get(sk.getKLines().size()-j).getTime());
                }
            }
            result.setMacdJincha(false);

            //统计unDisk 数目
            int unDiskTotal=0;
            for (int j=1;j<=6;j++)
            {
                if (quota.getMacd()[quota.getMacd().length-1-j]>0)
                {
                    unDiskTotal++;
                }
            }
            result.setUnDist(unDiskTotal);
        }

        LianxuData data= TaLibUtil.parseAva20AndAVa60(sk.getKLines());

        //设置 20线 连续在60线 下面多少根K线
        result.setAva20AboveAva60Times(data.getAva20AboveAva60Times());
        result.setAva20belowAva60Times(data.getAva20belowAva60Times());

        if (!result.isMacdJincha() && result.getDist()<= QuantConst.jinchaSichaMaxDist && Float.parseFloat(result.getMa60Price())> Float.parseFloat(result.getMa20Price()))
        {
            result.setSichaAnd60Greater20(true);
        }

        if (result.isMacdJincha() && result.getDist()<=QuantConst.jinchaSichaMaxDist && Float.parseFloat(result.getMa20Price())> Float.parseFloat(result.getMa60Price()))
        {
            result.setJinchaAnd20Greater60(true);
        }


        return result;

    }


}
