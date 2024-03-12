package com.mzwise.message.support;

import com.mzwise.message.SendMessageStrategy;
import org.springframework.stereotype.Component;

/**
 * 发送短信消息
 *
 * @author wmf
 */
@Component
public class SendSmsMessageService implements SendMessageStrategy {
    @Override
    public void send(Long memberId, String title, String content) {

    }
}
