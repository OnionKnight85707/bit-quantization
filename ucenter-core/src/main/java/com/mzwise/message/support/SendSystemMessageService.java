package com.mzwise.message.support;

import com.mzwise.common.util.SpringUtil;
import com.mzwise.constant.MessageChannelEnum;
import com.mzwise.constant.SysConstant;
import com.mzwise.message.SendMessageStrategy;
import com.mzwise.modules.ucenter.service.UcMessageCenterService;
import com.mzwise.netty.WebSocketServer;
import org.springframework.stereotype.Component;

/**
 * 发送系统消息
 *
 * @author wmf
 */
@Component
public class SendSystemMessageService implements SendMessageStrategy {
    @Override
    public void send(Long memberId, String title, String content) {
        UcMessageCenterService ucMessageCenterService = SpringUtil.getBean(UcMessageCenterService.class);
        ucMessageCenterService.sendMessage(memberId, title, content);
        // 发送webSocket消息
        WebSocketServer.sendMessage(SysConstant.SOCKET_REMIND_USER_PREFIX + memberId, title + "!&!" + content);
    }
}
