package com.mzwise.message;

/**
 * @author wmf
 */
public interface SendMessageStrategy {
    /**
     * 给指定用户发送信息
     * @param memberId
     * @param title
     * @param content
     */
    void send(Long memberId, String title, String content);
}
