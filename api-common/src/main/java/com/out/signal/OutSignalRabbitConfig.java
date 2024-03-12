package com.out.signal;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author :
 * @Description :
 **/

@Configuration
public class OutSignalRabbitConfig {

    /**
     * 用户信息
     */
    public final static String outSignalQueue = "topic.signal.out";

    @Bean
    public Queue outQueue() {
        return new Queue(OutSignalRabbitConfig.outSignalQueue, true);
    }

    @Bean
    TopicExchange outExchange() {
        return new TopicExchange("exchange");
    }


    @Bean
    Binding bindingOutMessage() {
        return BindingBuilder.bind(outQueue()).to(outExchange()).with(outSignalQueue);
    }

}
