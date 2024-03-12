package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcExchange;
import com.mzwise.modules.ucenter.mapper.UcExchangeMapper;
import com.mzwise.modules.ucenter.service.UcExchangeService;
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
public class UcExchangeServiceImpl extends ServiceImpl<UcExchangeMapper, UcExchange> implements UcExchangeService {

    @Override
    public Page<UcExchange> list(Long memberId, Integer pageNum, Integer pageSize) {
        Page<UcExchange> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcExchange> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcExchange::getMemberId, memberId)
                .orderByDesc(UcExchange::getCreateTime);
        return page(page, wrapper);
    }
}
