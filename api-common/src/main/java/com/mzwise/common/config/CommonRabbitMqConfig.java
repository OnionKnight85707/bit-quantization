package com.mzwise.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonRabbitMqConfig {

    public final static String frozenQueue = "topic.settle.frozen";


    public final static String exchange="exchange";

    @Bean
    public Queue frozenQueue() {
        return new Queue(frozenQueue, true);
    }

   // @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchange);
    }


    @Bean
    Binding frozenBindingExchangeMessage() {
        return BindingBuilder.bind(frozenQueue()).to(exchange()).with(frozenQueue);
    }

}
