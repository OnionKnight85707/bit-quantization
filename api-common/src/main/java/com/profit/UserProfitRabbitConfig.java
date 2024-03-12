package com.profit;

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
public class UserProfitRabbitConfig {

    /**
     * 用户信息
     */
    public final static String profitQueue = "topic.user.profit";

    @Bean
    public Queue profitQueue() {
        return new Queue(UserProfitRabbitConfig.profitQueue, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }


    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(profitQueue()).to(exchange()).with(profitQueue);
    }

}
