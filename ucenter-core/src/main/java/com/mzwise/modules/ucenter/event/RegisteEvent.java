package com.mzwise.modules.ucenter.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 注册事件
 * @author wmf
 */
@Component
@Getter
@Setter
public class RegisteEvent extends ApplicationEvent {

    private Long memberId;

    public RegisteEvent(ApplicationContext source) {
        super(source);
    }

}

