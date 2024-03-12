package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcTransfer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
public interface UcTransferService extends IService<UcTransfer> {
    /**
     * 划转明细
     * @return
     */
    Page<UcTransfer> list(Long memberId, Integer pageNum, Integer pageSize);
}
