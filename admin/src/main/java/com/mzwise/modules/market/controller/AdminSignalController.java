package com.mzwise.modules.market.controller;

import com.alibaba.fastjson.JSONObject;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.PositionSideEnum;
import com.mzwise.constant.TradeTypeEnum;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Api(tags = "信号控制台")
@RestController
@RequestMapping("/admin/signal")
public class AdminSignalController {

    private Logger log = LoggerFactory.getLogger(AdminSignalController.class);

    private final static String QUEUE_NAME = "quantify"; //队列名称
    private final static String EXCHANGE_NAME = "topic_logs"; //要使用的exchange的名称
    private final static String EXCHANGE_TYPE = "topic"; //要使用的exchange的名称
    private final static String EXCHANGE_ROUTING_KEY = "info"; //exchange使用的routing-key

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/strategy/buy")
    @ApiOperation("自设指标发出购买信号(切勿调用)")
    @AnonymousAccess
    public CommonResult strategyBuy(@RequestBody List<Long> sids) {
//        StrategySignal signal = new StrategySignal(DirectionParamEnum.LONG, sids, System.currentTimeMillis() + 5000);
//        rabbitTemplate.convertAndSend("exchange", "topic.strategy.signal",
//                JSON.toJSONString(signal)
//        );
        return CommonResult.success();
    }

    @PostMapping("/strategy/sell")
    @ApiOperation("自设指标发出卖出信号(切勿调用)")
    @AnonymousAccess
    public CommonResult strategySell(@RequestBody List<Long> sids) {
//        StrategySignal signal = new StrategySignal(DirectionParamEnum.SHORT, sids, System.currentTimeMillis() + 5000);
//        rabbitTemplate.convertAndSend("exchange", "topic.strategy.signal",
//                JSON.toJSONString(signal)
//        );
        return CommonResult.success();
    }

    @PostMapping("/trust/buy")
    @ApiOperation("托管发出购买信号(切勿调用)")
    @AnonymousAccess
    public CommonResult trustBuy(@RequestBody TrustParam param) throws IOException, TimeoutException {
        send(param, PositionSideEnum.LONG);
        return CommonResult.success();
    }

    @PostMapping("/trust/sell")
    @ApiOperation("托管发出卖出信号(切勿调用)")
    @AnonymousAccess
    public CommonResult trustSell(@RequestBody TrustParam param) throws IOException, TimeoutException {
        send(param, PositionSideEnum.SHORT);
        return CommonResult.success();
    }

    public static void send(TrustParam param, PositionSideEnum positionSide) throws IOException, TimeoutException {//创建连接工厂

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("8.210.113.112"); //设置rabbitmq-server的地址
        connectionFactory.setPort(5672);  //使用的端口号
        connectionFactory.setUsername("gbt");
        connectionFactory.setPassword("gbt123456");
        connectionFactory.setVirtualHost("/");  //使用的虚拟主机

        //由连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //通过连接创建信道
        Channel channel = connection.createChannel();

        //通过信道声明一个exchange，若已存在则直接使用，不存在会自动创建
        //参数：name、type、是否支持持久化、此交换机没有绑定一个queue时是否自动删除、是否只在rabbitmq内部使用此交换机、此交换机的其它参数（map）
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE, false, false, false, null);

        //通过信道声明一个queue，如果此队列已存在，则直接使用；如果不存在，会自动创建
        //参数：name、是否支持持久化、是否是排它的、是否支持自动删除、其他参数（map）
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //将queue绑定至某个exchange。一个exchange可以绑定多个queue
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, EXCHANGE_ROUTING_KEY);

//        {"type": "future", "instrument_id": "ETH-USDT", "buy_signals": [0, 4, 10], "sell_signals": [1, 3], "time": "2021-07-06 12:15:06"}
        com.mzwise.quant.param.TrustSignal trustSignal = new com.mzwise.quant.param.TrustSignal();
        trustSignal.setInstrument_id(param.getSymbol());
        if (param.getTradeType().equals(TradeTypeEnum.EXCHANGE)) {
            trustSignal.setType("spot");
        } else {
            trustSignal.setType("future");
        }
        if (positionSide.equals(PositionSideEnum.LONG)) {
            trustSignal.setBuy_signals(param.getSids());
        } else {
            trustSignal.setSell_signals(param.getSids());
        }

//        trustSignal.setTime();

        String msg = JSONObject.toJSONString(trustSignal); //消息内容

        channel.basicPublish(EXCHANGE_NAME, EXCHANGE_ROUTING_KEY, null ,msg.getBytes()); //消息是byte[]，可以传递所有类型(转换为byte[])，不局限于字符串

        System.out.println("send message："+msg);//关闭连接

        channel.close();

        connection.close();

    }
}