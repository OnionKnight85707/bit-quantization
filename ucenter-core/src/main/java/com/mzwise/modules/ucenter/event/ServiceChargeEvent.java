package com.mzwise.modules.ucenter.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/05/26
 */
@Getter
public class ServiceChargeEvent extends ApplicationEvent {
    private final Long memberId;
    private final BigDecimal amount;

    public ServiceChargeEvent(Object source, Long memberId, BigDecimal amount) {
        super(source);
        this.memberId = memberId;
        this.amount = amount;
    }
}
