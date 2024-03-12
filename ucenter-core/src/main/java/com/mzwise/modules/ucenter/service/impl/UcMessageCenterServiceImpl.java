package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.entity.UcMessageCenter;
import com.mzwise.modules.ucenter.mapper.UcMessageCenterMapper;
import com.mzwise.modules.ucenter.service.UcMessageCenterService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Service
public class UcMessageCenterServiceImpl extends ServiceImpl<UcMessageCenterMapper, UcMessageCenter> implements UcMessageCenterService {

    @Override
    public Page<UcMessageCenter> listMyMessages(Long memberId, Integer pageNum, Integer pageSize) {
        Page<UcMessageCenter> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcMessageCenter> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcMessageCenter::getMemberId, memberId)
                .orderByDesc(UcMessageCenter::getHasRead, UcMessageCenter::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public Page<UcMessageCenter> listMessages(Integer pageNum, Integer pageSize) {
        Page<UcMessageCenter> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcMessageCenter> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(UcMessageCenter::getHasRead, UcMessageCenter::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public void sendMessage(Long memberId, String title, String content) {
        UcMessageCenter message = new UcMessageCenter();
        message.setMemberId(memberId);
        message.setTitle(title);
        message.setContent(content);
        message.setMemberId(memberId);
        save(message);
    }

    @Override
    public void readMessage(Long messageId) {
        UpdateWrapper<UcMessageCenter> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(UcMessageCenter::getId, messageId);
        wrapper.lambda().set(UcMessageCenter::getHasRead, true);
        update(wrapper);
    }

    @Override
    public void readAll(Long memberId) {
        UpdateWrapper<UcMessageCenter> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(UcMessageCenter::getMemberId, memberId);
        wrapper.lambda().set(UcMessageCenter::getHasRead, true);
        update(wrapper);
    }

    @Override
    public Integer countUnRead(Long memberId) {
        QueryWrapper<UcMessageCenter> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcMessageCenter::getMemberId, memberId)
                .eq(UcMessageCenter::getHasRead, false);
        return count(wrapper);
    }


}
