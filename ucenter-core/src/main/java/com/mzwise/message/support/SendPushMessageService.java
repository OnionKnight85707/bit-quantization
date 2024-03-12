package com.mzwise.message.support;

import com.mzwise.common.util.SpringUtil;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.message.SendMessageStrategy;
import com.mzwise.modules.ucenter.service.UcMemberService;
import org.springframework.stereotype.Component;

/**
 * 发送推送消息
 *
 * @author piao
 */
@Component
public class SendPushMessageService implements SendMessageStrategy {
    @Override
    public void send(Long memberId, String title, String content) {

    }
}
