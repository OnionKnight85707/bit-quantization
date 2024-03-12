package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcMessageCenter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface UcMessageCenterService extends IService<UcMessageCenter> {
    /**
     * 分页展示我的所有消息列表
     *
     * @param memberId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<UcMessageCenter> listMyMessages(Long memberId, Integer pageNum, Integer pageSize);

    /**
     * 后台查看所有消息列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<UcMessageCenter> listMessages(Integer pageNum, Integer pageSize);

    /**
     * 给某人发送消息
     *
     * @param memberId
     * @return
     */
    void sendMessage(Long memberId, String title, String content);
    /**
     * 已读
     *
     * @param messageId
     */
    void readMessage(Long messageId);

    /**
     * 全部已读
     *
     * @param memberId
     */
    void readAll(Long memberId);

    /**
     * 未读消息数量
     *
     * @param memberId
     * @return
     */
    Integer countUnRead(Long memberId);
}
