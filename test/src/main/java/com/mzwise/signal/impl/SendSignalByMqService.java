package com.mzwise.signal.impl;

import com.mzwise.config.ExchangeRabbitConfig;
import com.mzwise.out.SendSignalService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendSignalByMqService implements SendSignalService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String signal) {
        rabbitTemplate.convertAndSend("exchange", ExchangeRabbitConfig.signal,
                signal
        );
    }
}
