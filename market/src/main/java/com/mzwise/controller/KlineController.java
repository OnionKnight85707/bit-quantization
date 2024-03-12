package com.mzwise.controller;

import com.alibaba.fastjson.JSONArray;
import com.mzwise.huobi.market.socket.entity.KLine;
import com.mzwise.service.KlineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kline")
public class KlineController {

    @Autowired
    private KlineService klineService;

    private Logger log = LoggerFactory.getLogger(KlineController.class);

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray findKHistory(String symbol, Long start, Long end, @RequestParam String period, String type) {
        if (period.endsWith("H") || period.endsWith("h")) {
            period = period.substring(0, period.length() - 1) + "hour";
        } else if (period.endsWith("D") || period.endsWith("d")) {
            period = period.substring(0, period.length() - 1) + "day";
        } else if (period.endsWith("W") || period.endsWith("w")) {
            period = period.substring(0, period.length() - 1) + "week";
        } else if (period.endsWith("M") || period.endsWith("m")) {
            period = period.substring(0, period.length() - 1) + "mon";
        } else {
            Integer val = Integer.parseInt(period);
            if (val <= 60) {
                period = period + "min";
            } else {
                period = (val / 60) + "hour";
            }
        }
        Long from = start == null ? null : start / 1000;
        Long to = end == null ? null : end / 1000;
        List<KLine> list = klineService.findAllKLine(symbol, period, from, to);
        log.info("获取历史K线）symbol: {},  period: {}, from: {}, to: {}, size: {}", symbol, period, from, to, list.size());
        JSONArray array = new JSONArray();
        if ("timeline".equals(type)) { // 分时
            for (KLine item : list) {
                JSONArray group = new JSONArray();
                group.add(0, item.getTime());
                group.add(1, item.getClose());
                group.add(2, item.getVolume());
                array.add(group);
            }
        } else {
            // todo 少一根正在生成的k
            // 加上正在生成的k线线
//            KLine newKLine = klineStorage.get(symbol, period);
//            if (newKLine!=null) {
//                Long periodStartTime = PeriodUtil.getPeriodStartTime(period);
//                newKLine.setDate(periodStartTime);
//                newKLine.set_id(periodStartTime);
//                if (newKLine!=null) {
//                    list.add(newKLine);
//                }
//            }
            for (KLine item : list) {
                JSONArray group = new JSONArray();
                group.add(0, item.getTime());
                group.add(1, item.getOpen());
                group.add(2, item.getHigh());
                group.add(3, item.getLow());
                group.add(4, item.getClose());
                group.add(5, item.getVolume());
                array.add(group);
            }
        }
        return array;
    }

}
