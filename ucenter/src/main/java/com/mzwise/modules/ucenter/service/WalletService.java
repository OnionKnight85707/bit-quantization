package com.mzwise.modules.ucenter.service;

import com.mzwise.modules.ucenter.dto.ScanCodeTransferParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author piao
 * @Date 2021/06/02
 */
public interface WalletService {
    /**
     * 扫码转账
     *
     * @param fromMemberId
     * @param param
     */
    @Transactional(rollbackFor = Exception.class)
    void scanCodeTransfer(Long fromMemberId, ScanCodeTransferParam param);
}
