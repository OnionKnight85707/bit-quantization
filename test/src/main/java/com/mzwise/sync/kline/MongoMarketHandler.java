package com.mzwise.sync.kline;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MongoMarketHandler {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void handleKLine(String symbol, KLine kLine) {
        log.info("保存k线， symol: {}, period: {}, time: {}, kline: {}", symbol, kLine.getPeriod(), kLine.getTime(), kLine);
        mongoTemplate.insert(kLine, "exchange_kline_" + symbol + "_" + kLine.getPeriod());
    }

    public void saveNoExits(String symbol, KLine kLine) {
        if (findByTime(symbol, kLine.getPeriod(), kLine.getTime())==null) {
            log.info("保存k线， symol: {}, kline: {}", symbol, kLine);
            mongoTemplate.insert(kLine, "exchange_kline_" + symbol + "_" + kLine.getPeriod());
        }
    }

    public KLine findByTime(String symbol, String period, long time) {
        Query query = new Query();
        Criteria criteria = Criteria.where("time").is(time);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, KLine.class, "exchange_kline_" + symbol + "_" + period);
    }

    public long findMinTimestamp(String symbol, String period) {
        Sort sort = Sort.by(Sort.Order.asc("time"));
        Query query = new Query().with(sort).limit(1);
        List<KLine> result = mongoTemplate.find(query, KLine.class, "exchange_kline_" + symbol + "_" + period);
        if (result != null && result.size() > 0) {
            return result.get(0).getTime();
        } else {
            return 0;
        }
    }
}
