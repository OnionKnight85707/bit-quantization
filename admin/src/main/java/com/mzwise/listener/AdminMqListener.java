package com.mzwise.listener;

import com.alibaba.fastjson.JSONObject;
import com.mzwise.common.util.DateUtil;
import com.mzwise.modules.quant.service.impl.PriceService;
import com.mzwise.quant.param.ThumbSignal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@RabbitListener(containerFactory = "rabbitContainerFactory", bindings = {@QueueBinding(value = @Queue(value = "topic.strategy.admin_thumb", durable="false"), exchange = @Exchange(value = "thumb_exchange", type = "topic"))})
public class AdminMqListener {
    private Logger logger = LoggerFactory.getLogger(AdminMqListener.class);

    @Autowired
    private PriceService priceService;

    @RabbitHandler
    public void process(String msg) {
        try {
 //           logger.info("=== admin  ThumbMqListener 收到 行情信息: {}", msg);

            ThumbSignal signal = JSONObject.parseObject(msg, ThumbSignal.class);

            long timeMillis = DateUtil.getTimeMillis();
            // 时效已过期
            if (timeMillis>signal.getExp()) {
                return;
            }
            priceService.handleSignal(signal);
        } finally {

        }
    }
}
