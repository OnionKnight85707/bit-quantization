package com.mzwise.common.config;

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


    public final static String thumbSignal = "topic.strategy.admin_thumb";

    public final static String route = "topic.strategy.thumb";

    public final static String exchange="thumb_exchange";

    @Bean
    public Queue thumbQueue() {
        return new Queue(ExchangeRabbitConfig.thumbSignal, false);
    }

    @Bean
    TopicExchange  thumbExchange() {
        return new TopicExchange(exchange);
    }


    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(thumbQueue()).to(thumbExchange()).with(route);
    }

}
