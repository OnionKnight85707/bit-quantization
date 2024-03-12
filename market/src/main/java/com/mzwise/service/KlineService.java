package com.mzwise.service;

import com.mzwise.huobi.market.socket.entity.DateUnitEnum;
import com.mzwise.huobi.market.socket.entity.KLine;
import com.mzwise.huobi.market.socket.entity.PeriodEnum;
import com.mzwise.huobi.market.socket.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KlineService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<KLine> findAllKLine(String symbol, String period, Long start, Long end) {
        Criteria criteria = new Criteria();
        Boolean limit = false;
        if (start!=null && end==null) {
            criteria.and("time").gte(start);
        } else if (end!=null && start==null) {
            criteria.and("time").lte(end);
        } else if (start!=null && end!=null) {
            criteria.and("time").gte(start).andOperator(Criteria.where("time").lte(end));
        } else { // 两个都等于null
            start = defaultStartLimitByPeriod(period);
            criteria.and("time").gte(start);
        }
        Sort sort = Sort.by(Sort.Order.asc("time"));
        Query query = new Query(criteria).with(sort);
        List<KLine> kLines = mongoTemplate.find(query, KLine.class, "kline_" + symbol + "_" + period);
        return kLines;
    }

    public void saveKLine(String symbol, KLine kLine) {
        mongoTemplate.insert(kLine, "kline_" + symbol + "_" + kLine.getPeriod());
    }

//    public void saveKLines(String symbol, List<KLine> kLines) {
//        mongoTemplate.insert(kLines, "kline_" + symbol + "_" + kLines.get(0).getPeriod());
//    }

    /**
     * 获取K最近一条K线的时间
     *
     * @param symbol
     * @param period
     * @return
     */
    public long findMaxTimestamp(String symbol, PeriodEnum period) {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "time"));
        Query query = new Query().with(sort).limit(1);

        List<KLine> result = mongoTemplate.find(query, KLine.class, "kline_" + symbol + "_" + period.getCode());

        if (result != null && result.size() > 0) {
            return result.get(0).getTime();
        } else {
            return 0;
        }
    }

    private Long defaultStartLimitByPeriod(String period) {
        Long start = null;
        Date date = new Date();
        switch (period) {
            case "1min":
                start = DateUtil.changeTimestampSecoundByUnit(date, -6, DateUnitEnum.HOUR);
                break;
            case "5min":
                start = DateUtil.changeTimestampSecoundByUnit(date, -3, DateUnitEnum.DAY);
                break;
            case "15min":
                start = DateUtil.changeTimestampSecoundByUnit(date, -3, DateUnitEnum.DAY);
                break;
            case "30min":
                start = DateUtil.changeTimestampSecoundByUnit(date, -6, DateUnitEnum.DAY);
                break;
            case "60min":
                start = DateUtil.changeTimestampSecoundByUnit(date, -12, DateUnitEnum.DAY);
                break;
            case "4hour":
                start = DateUtil.changeTimestampSecoundByUnit(date, -48, DateUnitEnum.DAY);
                break;
            case "1day":
                start = DateUtil.changeTimestampSecoundByUnit(date, -268, DateUnitEnum.DAY);
                break;
            case "1week":
                start = DateUtil.changeTimestampSecoundByUnit(date, -268*7, DateUnitEnum.DAY);
                break;
            case "1mon":
                start = DateUtil.changeTimestampSecoundByUnit(date, -268*7*4, DateUnitEnum.DAY);
                break;
            default:
        }
        return start;
    }
}
