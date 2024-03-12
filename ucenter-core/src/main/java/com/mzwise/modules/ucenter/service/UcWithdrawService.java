package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcWithdraw;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.vo.WithDrawOptionVO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
public interface UcWithdrawService extends IService<UcWithdraw> {

    /**
     * 获取充币可选项
     * @param memberId
     * @return
     */
    List<WithDrawOptionVO> getOptions(Long memberId);

    /**
     * 提币明细
     * @param memberId
     * @param page
     * @param size
     * @return
     */
    Page<UcWithdraw> list(Long memberId, Integer page, Integer size);

    /**
     * 提交提币申请
     * @param memberId
     * @param walletType
     * @param address
     * @param amount
     */
    void apply(HttpServletRequest request, Long memberId, WalletTypeEnum walletType, String address, BigDecimal amount);

    /**
     * 提币成功
     * @param memberId
     * @param withdrawId
     * @param txid
     * @param amount
     * @param fee
     * @return
     */
    Boolean success(Long memberId, Long withdrawId, String txid, BigDecimal amount, BigDecimal fee, Long adminId);

    /**
     * 提币失败
     * @param withdrawId 提现id
     * @param adminId 审核人员id
     * @param refuseReason 拒绝原因
     * @return
     */
    Boolean fail(Long withdrawId, Long adminId, String refuseReason);
}
