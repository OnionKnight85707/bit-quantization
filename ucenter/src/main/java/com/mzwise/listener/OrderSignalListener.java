package com.mzwise.listener;

import com.alibaba.fastjson.JSONObject;
import com.mzwise.modules.ucenter.service.UcChargeService;
import com.mzwise.quant.param.OrderSignal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Slf4j
@Component
@RabbitListener(queues = "topic.order.signal")
public class OrderSignalListener {

    @RabbitHandler
    public void process(String msg) {
        OrderSignal signal = JSONObject.parseObject(msg, OrderSignal.class);
        // 开仓事件
        if ("OPEN".equals(signal.getType())) {
            log.info("收到用户开仓事件");
        }
        // 平仓事件
        if ("CLOSE".equals(signal.getType())) {
            log.info("收到用户平仓事件");
        }

    }
}
