package com.mzwise.signal.impl;

import com.mzwise.out.OrderSignalService;
import com.mzwise.quant.param.OrderSignal;
import org.springframework.stereotype.Service;

@Service
public class OrderSignalByMqService implements OrderSignalService {

    @Override
    public void send(OrderSignal signal) {
    }
}
