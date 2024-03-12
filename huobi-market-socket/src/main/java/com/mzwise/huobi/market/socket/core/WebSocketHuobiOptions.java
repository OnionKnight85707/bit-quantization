package com.mzwise.huobi.market.socket.core;

import com.mzwise.huobi.market.socket.entity.TopicEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WebSocketHuobiOptions {

    /**
     * 订阅主题
     */
    private List<TopicEnum> topics = new ArrayList<>();



    public WebSocketHuobiOptions(TopicEnum... topics) {
        if (topics.length==0) {
            this.topics = new ArrayList () {{
                add(TopicEnum.KLINE);
                add(TopicEnum.DEPTH);
                add(TopicEnum.TRADE);
                add(TopicEnum.THUMB);
            }};
        } else {
            this.topics = new ArrayList<>();
            for (TopicEnum topic : topics) {
                this.topics.add(topic);
            }
        }
    }
}
