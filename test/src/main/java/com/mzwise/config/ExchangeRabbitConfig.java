package com.mzwise.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : wmf
 * @Description :
 **/

@Configuration
public class ExchangeRabbitConfig {

    /**
     * 交易信号
     */
    public final static String signal = "topic.test.signal";

    @Bean
    public Queue signalQueue() {
        return new Queue(ExchangeRabbitConfig.signal, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }


    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(signalQueue()).to(exchange()).with(signal);
    }

}
