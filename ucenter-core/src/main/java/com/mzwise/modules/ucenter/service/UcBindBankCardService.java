package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.dto.UcBindBankCardParam;
import com.mzwise.modules.ucenter.entity.UcBindBankCard;
import com.mzwise.modules.ucenter.vo.BindBankCardVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface UcBindBankCardService extends IService<UcBindBankCard> {
    /**
     * 绑定银行卡
     * @param bindBankCardParam
     * @return
     */
    void bindBankCard(UcBindBankCardParam bindBankCardParam, Long memberId);

    /**
     * 获取我的银行卡列表
     * @return
     */
    Page<UcBindBankCard> listMyCards(Long memberId, Integer pageNum, Integer pageSize);

    /**
     * 展示所有银行卡列表
     * @param nickname
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<BindBankCardVO> listAllCard(String nickname, Integer pageNum, Integer pageSize);

}
