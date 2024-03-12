package com.mzwise.job;

import com.mzwise.huobi.market.socket.entity.Thumb;
import com.mzwise.netty.MarketSocketServer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@EnableAsync
public class ThumbPushJob {

    private Map<String, List<Thumb>> thumbQueue = new HashMap<>();

    public void addThumb(String symbol, Thumb thumb) {
        List<Thumb> list = thumbQueue.get(symbol);
        if (list == null) {
            list = new ArrayList<>();
            thumbQueue.put(symbol, list);
        }
        synchronized (list) {
            list.add(thumb);
        }
    }

    @Scheduled(fixedRate = 300)
    @Async
    public void pushThumb() {
        Iterator<Map.Entry<String, List<Thumb>>> entryIterator = thumbQueue.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, List<Thumb>> entry = entryIterator.next();
            String symbol = entry.getKey();
            List<Thumb> thumbs = entry.getValue();
            synchronized (thumbs) {
                if (thumbs.size() > 0) {
                    Thumb thumb = thumbs.get(thumbs.size() - 1);
                    String body = thumb.toString();
                    MarketSocketServer.sendMessage(symbol, body);
                    MarketSocketServer.sendMessage("ALL", body);
                    thumbs.clear();
                }
            }
        }
    }
}
