package com.profit;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Author wmf
 * @Date 2021/7/6 14:02
 * @Description
 */
@Configuration
public class CommonRabbitmqConfig {

    @Bean(name = "rabbitTemplate")
    @Primary
    public RabbitTemplate rabbitTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }


    @Bean("connectionFactory")
    @Primary
    public ConnectionFactory innerRabbitConfiguration(
            @Value("${spring.rabbitmq.host}") String host,
            @Value("${spring.rabbitmq.port}") int port,
            @Value("${spring.rabbitmq.username}") String username,
            @Value("${spring.rabbitmq.password}") String password,
            @Value("${spring.rabbitmq.virtual-host}") String virtualHost) {
        return this.rabbitConnection(host, port, username, password, null);
    }


    @Bean("rabbitContainerFactory")
    @Primary
    public SimpleRabbitListenerContainerFactory consumeRabbitFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        return this.rabbitFactory(configurer, connectionFactory);
    }




    public ConnectionFactory rabbitConnection(String host, int port, String username, String password, String virtualHost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        if (virtualHost!=null) {
            connectionFactory.setVirtualHost(virtualHost);
        }
        return connectionFactory;
    }

    public SimpleRabbitListenerContainerFactory rabbitFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
