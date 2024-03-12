package com.mzwise.common.util;

import com.mzwise.constant.MessageChannelEnum;
import com.mzwise.message.SendMessageStrategy;
import com.mzwise.message.support.SendPushMessageService;
import com.mzwise.message.support.SendSmsMessageService;
import com.mzwise.message.support.SendSystemMessageService;

import java.util.Arrays;
import java.util.ServiceLoader;
import java.util.concurrent.*;

/**
 * @Author Administrator
 * @Date 2021/02/01
 */
public class MessageUtil {

    private static ExecutorService service = Executors.newFixedThreadPool(1);
    private static final SendSystemMessageService sendSystemMessageService;
    private static final SendPushMessageService sendPushMessageService;
    private static final SendSmsMessageService sendSmsMessageService;

    static {
        sendSystemMessageService = SpringUtil.getBean(SendSystemMessageService.class);
        sendPushMessageService = SpringUtil.getBean(SendPushMessageService.class);
        sendSmsMessageService = SpringUtil.getBean(SendSmsMessageService.class);
    }

    public static void send(Long memberId, String title, String content, MessageChannelEnum... channelEnums) {
        service.execute(() -> {
            if (channelEnums.length == 0) {
                sendSystemMessageService.send(memberId, title, content);
            }
            for (MessageChannelEnum channelEnum : channelEnums) {
                if (channelEnum.equals(MessageChannelEnum.SYS_MESSAGE)) {
                    sendSystemMessageService.send(memberId, title, content);
                }
                if (channelEnum.equals(MessageChannelEnum.SMS)) {
                    sendSmsMessageService.send(memberId, title, content);
                }
                if (channelEnum.equals(MessageChannelEnum.APP_PUSH)) {
                    sendPushMessageService.send(memberId, title, content);
                }
            }
        });
    }
}
