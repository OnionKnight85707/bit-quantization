package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcTransfer;
import com.mzwise.modules.ucenter.mapper.UcTransferMapper;
import com.mzwise.modules.ucenter.service.UcTransferService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
@Service
public class UcTransferServiceImpl extends ServiceImpl<UcTransferMapper, UcTransfer> implements UcTransferService {

    @Override
    public Page<UcTransfer> list(Long memberId, Integer pageNum, Integer pageSize) {
        Page<UcTransfer> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcTransfer> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcTransfer::getMemberId, memberId)
                .orderByDesc(UcTransfer::getCreateTime);
        return page(page, wrapper);
    }
}
