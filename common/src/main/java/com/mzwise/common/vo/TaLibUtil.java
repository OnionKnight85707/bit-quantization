package com.mzwise.common.vo;



import com.mzwise.common.vo.kdj.KdjOutQuota;
import com.mzwise.common.vo.macd.MacdOutQuota;


import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.apache.commons.lang3.ArrayUtils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TaLibUtil {



    private static Core tabLib;

    private static DecimalFormat df=new DecimalFormat("#.00");

    static {
        tabLib = new Core();
    }
    /**
     * 计算macd
     * @param klines
     * @param fastPeriod
     * @param slowPeriod
     * @param signalPeriod
     * @return
     */
    public static MacdOutQuota macd(List<KLine> klines, int fastPeriod,
                                    int slowPeriod,
                                    int signalPeriod) {
        try {
            MInteger outBegIdx = new MInteger();
            MInteger outNBElement = new MInteger();
            double[] outMACD = new double[klines.size()];
            double[] outMACDSignal = new double[klines.size()];
            double[] outMACDHist = new double[klines.size()];
            List<Double> closes = klines.stream().map((v) -> v.getClose().doubleValue()).collect(Collectors.toList());
            Double[] closesArrays = closes.toArray(new Double[0]);
            double[] closePrice = ArrayUtils.toPrimitive(closesArrays);
            RetCode retCode = tabLib.macd(0, klines.size() - 1, closePrice, fastPeriod, slowPeriod, signalPeriod,
                    outBegIdx, outNBElement, outMACD, outMACDSignal, outMACDHist);
            if (retCode == RetCode.Success && outNBElement.value > 0) {
//                // todo 获取时间有点耗费时间，如不用可去掉
//                long[] times = new long[klines.size() - outBegIdx.value];
//                for (int i = outBegIdx.value; i < outBegIdx.value + outNBElement.value; i++) {
//                    times[i-outBegIdx.value] = klines.get(i).getTime();
//                }
                MacdOutQuota macd = MacdOutQuota.builder()
//                        .time(times)
                        .macd(Arrays.copyOf(outMACDHist, outMACDHist.length - outBegIdx.value))
                        .dif(Arrays.copyOf(outMACD, outMACD.length - outBegIdx.value))
                        .dea(Arrays.copyOf(outMACDSignal, outMACDSignal.length - outBegIdx.value)).build();
                return macd;
            }
            else {
               // logger.error("{}，MACD计算错误， 个数： {}", retCode.toString(), outNBElement.value);
                return null;
            }
        } catch (Exception e) {
           // logger.error("{}，MACD计算代码出现错误");
            return null;
        }
    }


    /**
     * 计算cci
     * @param klines
     */
    public static double[] cci(List<KLine> klines, int timePeriod) {
        try {
            double[] outReal = new double[klines.size()];
            MInteger outBegIdx = new MInteger();
            MInteger outNBElement = new MInteger();
            double inHigh[] = new double[klines.size()];
            double inLow[] = new double[klines.size()];
            double inClose[] = new double[klines.size()];
            for (int i=0;i<klines.size();i++) {
                inHigh[i] = klines.get(i).getHigh().doubleValue();
                inLow[i] = klines.get(i).getLow().doubleValue();
                inClose[i] = klines.get(i).getClose().doubleValue();
            }
            RetCode retCode = tabLib.cci(0, klines.size() - 1, inHigh, inLow, inClose, timePeriod, outBegIdx, outNBElement, outReal);
            if (retCode == RetCode.Success && outNBElement.value > 0) {
//                System.out.println(outReal);

                return Arrays.copyOf(outReal, outReal.length - outBegIdx.value);
            }
            else {
               // logger.error("{}，CCI计算错误， 个数： {}", retCode.toString(), outNBElement.value);
                return null;
            }
        } catch (Exception e) {
          //  logger.error("{}，CCI计算代码出现错误");
            return null;
        }

    }



    /**
     * 计算EMA
     * @param klines
     */
    public static double[] ema(List<KLine> klines, int timePeriod) {
        try {
            double[] outReal = new double[klines.size()];
            MInteger outBegIdx = new MInteger();
            MInteger outNBElement = new MInteger();
            List<Double> collect = klines.stream().map((v) -> v.getClose().doubleValue()).collect(Collectors.toList());
            Double[] collectArray=collect.toArray(new Double[0]);
            double[] closePrice = ArrayUtils.toPrimitive(collectArray);
            RetCode retCode = tabLib.ema(0, klines.size() - 1, closePrice, timePeriod, outBegIdx, outNBElement, outReal);
            if (retCode == RetCode.Success && outNBElement.value > 0) {
                return Arrays.copyOf(outReal, outReal.length - outBegIdx.value);
            }
            else {
              //  logger.error("{}，EMA计算错误， 个数： {}", retCode.toString(), outNBElement.value);
                return null;
            }
        } catch (Exception e) {
          //  logger.error("{}，EMA计算代码出现错误");
            return null;
        }
    }

    /**
     * 计算KDJ
     * @param klines
     */
    public static KdjOutQuota kdj(List<KLine> klines, int fastKPeriod,
                                  int slowKPeriod,
                                  int slowDPeriod) {
        try {
            List<BigDecimal> rsv = calcRSV(fastKPeriod, klines);
            List<BigDecimal> k = calcSMA(slowKPeriod, 1, rsv);
            List<BigDecimal> d = calcSMA(slowDPeriod,1,k);
            List<BigDecimal> j = new ArrayList<>();
            for(int i=0;i<k.size();i++){
                j.add(k.get(i).multiply(BigDecimal.valueOf(3)).subtract(d.get(i).multiply(BigDecimal.valueOf(2))));
            }
            return KdjOutQuota.builder()
                    .k(toDoubles(k))
                    .d(toDoubles(d))
                    .j(toDoubles(j))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
           // logger.error("{}，KDJ计算代码出现错误");
            return null;
        }
    }

    private static double[] toDoubles(List<BigDecimal> datas) {
        double[] re = new double[datas.size()];
        for(int i=0;i<datas.size();i++) {
            re[i] = datas.get(i).doubleValue();
        }
        return re;
    }

    private static List<BigDecimal> calcRSV(int fastKPeriod, List<KLine> klines) {
        List<BigDecimal> rsv = new ArrayList<>();
        for(int i = 0;i<klines.size();i++){
            BigDecimal low=calcLLV(i,fastKPeriod,klines,"low");
            BigDecimal tempValue;
            try {
                tempValue=BigDecimal.valueOf(100).multiply((klines.get(i).getClose().subtract(low)).divide(calcHHV(i,fastKPeriod,klines,"high").subtract(low), 8, RoundingMode.UP));
            } catch (Exception e) {
                if(i==0){
                    tempValue=BigDecimal.valueOf(100);
                }else{
                    tempValue=rsv.get(i-1);
                }
            }
            rsv.add(tempValue);
        }
        return rsv;
    }

    /*
     * 计算SMA，加权移动平均指标
     * @param {number} n 时间窗口
     * @param {number} m 权重
     * @param {array} data 输入数据
     */
    private static List<BigDecimal> calcSMA(int n, int m, List<BigDecimal> data){
        ArrayList<BigDecimal> sma = new ArrayList<BigDecimal>() {{
            add(BigDecimal.valueOf(100));
        }};
        for(int i=1;i<data.size();i++){
            sma.add(((data.get(i).multiply(BigDecimal.valueOf(m)).add(sma.get(i-1).multiply(BigDecimal.valueOf(n-m)))).divide(BigDecimal.valueOf(n), 8, RoundingMode.HALF_DOWN)));
        }
        return sma;
    };

    /*
     * 计算最小值
     * @param {number} pos 最新值索引
     * @param {number} n 取最小值范围周期
     * @param {array} data 输入数据
     * @param {string} field 计算字段配置
     */
    private static BigDecimal calcLLV(int pos,int n,List<KLine> klines, String field) {
        n--;
        BigDecimal min = klines.get(pos).getField(field);
        int l=pos-n;
        if (l<0) {
            l = 0;
        }
        for(int i=pos;i>=l;i--){
            if(min.compareTo(klines.get(i).getField(field))>0){
                min=klines.get(i).getField(field);
            }
        }
        return min;
    }

    /*
     * 计算最大值
     * @param {number} pos 最新值索引
     * @param {number} n 取最大值范围周期
     * @param {array} data 输入数据
     * @param {string} field 计算字段配置
     */
    private static BigDecimal calcHHV(int pos,int n,List<KLine> klines, String field) {
        n--;
        BigDecimal max = klines.get(pos).getField(field);
        int l=pos-n;
        if (l<0) {
            l = 0;
        }
        for(int i=pos;i>=l;i--){
            if(max.compareTo(klines.get(i).getField(field))<0){
                max=klines.get(i).getField(field);
            }
        }
        return max;
    }

    /**
     * 计算 这些K线的 某均线的 ma值，比如20线值是多少。,时间大的在后面
     * @param klines
     * @param ma
     * @return
     */
    public static BigDecimal ma(List<KLine> klines, int ma) {

        BigDecimal total=BigDecimal.ZERO;
        for (int i=0;i<klines.size()-1 && i<ma ;i++)
        {
            total=total.add(klines.get(klines.size()-1-i).getClose());
        }

        return total.divide(new BigDecimal(""+ma),8,BigDecimal.ROUND_DOWN);

    }

    public static MaItem parseMa(List<KLine> klines, int ma) {

        BigDecimal total=BigDecimal.ZERO;
        for (int i=0;i<klines.size()-1 && i<ma ;i++)
        {
            total=total.add(klines.get(klines.size()-1-i).getClose());
        }

        float newMaValue= total.divide(new BigDecimal(""+ma),8,BigDecimal.ROUND_DOWN).floatValue();


        total=BigDecimal.ZERO;
        for (int i=0;i<klines.size()-1 && i<ma ;i++)
        {
            total=total.add(klines.get(klines.size()-1-i-1).getClose());
        }
        float secondMaValue= total.divide(new BigDecimal(""+ma),8,BigDecimal.ROUND_DOWN).floatValue();

        MaItem result=new MaItem();
        result.setMaPrice(newMaValue);
        result.setUp(newMaValue>secondMaValue);

        KLine last1K=klines.get(klines.size()-1);
        KLine last2K=klines.get(klines.size()-2);
        KLine last3K=klines.get(klines.size()-3);
        KLine last4K=klines.get(klines.size()-4);
        KLine last5K=klines.get(klines.size()-5);
        KLine last6K=klines.get(klines.size()-6);
        float last1P=(last1K.getClose().floatValue()-last2K.getClose().floatValue())/last2K.getClose().floatValue()*100;
        float last2P=(last2K.getClose().floatValue()-last3K.getClose().floatValue())/last3K.getClose().floatValue()*100;
        float last3P=(last3K.getClose().floatValue()-last4K.getClose().floatValue())/last4K.getClose().floatValue()*100;
        float last4P=(last4K.getClose().floatValue()-last5K.getClose().floatValue())/last5K.getClose().floatValue()*100;
        float last5P=(last5K.getClose().floatValue()-last6K.getClose().floatValue())/last6K.getClose().floatValue()*100;

        result.setLast1Percent(Float.parseFloat(df.format(last1P)));
        result.setLast2Percent(Float.parseFloat(df.format(last2P)));
        result.setLast3Percent(Float.parseFloat(df.format(last3P)));
        result.setLast4Percent(Float.parseFloat(df.format(last4P)));
        result.setLast5Percent(Float.parseFloat(df.format(last5P)));


        return result;


    }

    public static LianxuData parseAva20AndAVa60(List<KLine> klines)
    {
        /**
         * 最近时间的排在 最前面。
         */
        List<BigDecimal> ava60List=new ArrayList<>();
        List<BigDecimal> ava20List=new ArrayList<>();

        BigDecimal sum60=BigDecimal.ZERO;
        BigDecimal sum20=BigDecimal.ZERO;
        for (int i=0;i< klines.size() && i<120;i++)
        {
            if (i<20)
            {
                sum20=sum20.add(klines.get(klines.size()-1-i).getClose());
            }else if (i>=20)
            {
                ava20List.add(sum20.divide(new BigDecimal("20"),8,BigDecimal.ROUND_HALF_EVEN));
                sum20= sum20.subtract(klines.get(klines.size()-1-i+20).getClose());
                sum20=sum20.add(klines.get(klines.size()-1-i).getClose());
            }

            if (i<60)
            {
                sum60=sum60.add(klines.get(klines.size()-1-i).getClose());
            }else if (i>=60)
            {
                ava60List.add(sum60.divide(new BigDecimal("60"),8,BigDecimal.ROUND_HALF_EVEN));
                sum60=sum60.subtract(klines.get(klines.size()-1-i+60).getClose());
                sum60=sum60.add(klines.get(klines.size()-1-i).getClose());
            }

        }

        LianxuData data=new LianxuData(0,0);
        int i=0;
        for( i=0;i<50;i++)
        {
            if(ava20List.get(i).compareTo(ava60List.get(i))>0)
            {
                break;
            }
        }
        data.setAva20belowAva60Times(i);

        for( i=0;i<50;i++)
        {
            if(ava20List.get(i).compareTo(ava60List.get(i))<0)
            {

                break;
            }
        }
        data.setAva20AboveAva60Times(i);
        return data;
    }



}
