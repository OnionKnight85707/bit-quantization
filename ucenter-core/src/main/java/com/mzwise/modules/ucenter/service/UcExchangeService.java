package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcExchange;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
public interface UcExchangeService extends IService<UcExchange> {

    /**
     * 分页展示兑换明细
     * @param memberId
     * @param page
     * @param size
     * @return
     */
    Page<UcExchange> list(Long memberId, Integer page, Integer size);
}
