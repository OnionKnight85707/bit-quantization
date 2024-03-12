package com.mzwise.huobi.market.socket.core.kline;

import com.mzwise.huobi.market.socket.core.HuobiBaseHandler;
import com.mzwise.huobi.market.socket.entity.PeriodEnum;
import com.mzwise.huobi.market.socket.util.DateUtil;
import com.mzwise.huobi.market.socket.core.WebSocketConnectionManage;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class KlineSyncJob implements Job {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(KlineSyncJob.class);

    /** 处理器 **/
    public static HuobiBaseHandler handler;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        // 有自动补充k线的功能
        Set<String> symbols = handler.symbols();
        // 获取当前时间(秒)
        Long currentTime = DateUtil.getTimeMillis() / 1000;
//        logger.info("分钟执行获取K线[Start]");
        for (String symbol : symbols) {
            for (PeriodEnum period : PeriodEnum.values()) {
                // TODO: 2021/4/10 这里的算法可以优化
                long fromTime = handler.findMaxTimestamp(symbol, period);
                long timeGap = currentTime - fromTime;
                if (timeGap >= period.gapTime()) {
                    reqKLineList(symbol, period, fromTime, currentTime);
                }
            }
        }
    }

    /**
     * 检查是否有新k线生成，并获取k线
     * @param symbol
     * @param period
     * @param fromTime
     * @param currentTime
     */
    private void reqKLineList(String symbol, PeriodEnum period, long fromTime, long currentTime) {
        // 分钟取整
        long timeGap = currentTime - fromTime;
        long toTime = fromTime + timeGap - (timeGap % period.gapTime()) - period.gapTime();
        if (fromTime == 0) {
            // todo 300根 k线基础数据。要做成可配
            long from = currentTime - period.gapTime() * 300;
            // 火币初始日期
            if (from < 1501174800) {
                from = 1501174800;
            }
//            logger.info("{} 分钟执行获取初始化K线[{}] ===> from == 0, from: {}, to: {}", symbol, period, from, toTime);
            WebSocketConnectionManage.getWebSocket().reqKLineList(symbol, period, from, toTime);
        } else {
//            logger.info("{} 分钟执行获取最新K线[{}] ===> from != 0, from: {}, to: {}", symbol, period, fromTime+1, toTime);
            // from +1秒避开上一个区间的k线
            WebSocketConnectionManage.getWebSocket().reqKLineList(symbol, period, fromTime+1, toTime);
        }
    }


//    public static void main(String[] args) {
//        long mon1 = 30 * 24 * 60 * 60l;
//        long c = DateUtil.getTimeMillis() / 1000;
//        System.out.println(c - 200*mon1);
//    }
}
