package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.quant.vo.AdminQuantMemberVO;
import com.mzwise.modules.ucenter.dto.ManualDepositAndWithdrawalParam;
import com.mzwise.modules.ucenter.vo.AdminMemberAssetsVO;
import com.mzwise.modules.ucenter.vo.AdminMemberDepositAndWithdrawalVO;
import com.mzwise.modules.ucenter.vo.AdminMemberHomePageVO;
import com.mzwise.modules.ucenter.vo.AdminMemberProfitVO;

/**
 * @Author piao
 * @Date 2021/05/31
 */
public interface AdminMemberDetailService {
    /**
     * 展示后台用户详情——主页
     *
     * @param memberId
     * @return
     */
    AdminMemberHomePageVO showHomePage(Long memberId);

    /**
     * 展示后台用户详情——资产
     *
     * @param memberId
     * @return
     */
    AdminMemberAssetsVO showMemberAssets(Long memberId);

    /**
     * 充提币管理
     *
     * @param memberId
     * @return
     */
    AdminMemberDepositAndWithdrawalVO showDepositAndWithdrawal(Long memberId);

    /**
     * 展示用户详情——收益
     *
     * @param memberId
     * @return
     */
    AdminMemberProfitVO showMemberProfitVO(Long memberId);

    /**
     * 后台手动充值
     *
     * @param param
     * @return
     */
    Boolean manualRecharge(ManualDepositAndWithdrawalParam param);

    /**
     * 后台是手动减币
     *
     * @param param
     * @return
     */
    Boolean manualWithdrawal(ManualDepositAndWithdrawalParam param);


}
