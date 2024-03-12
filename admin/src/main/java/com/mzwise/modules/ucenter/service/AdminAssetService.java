package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.RechargeTypeEnum;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcExchange;
import com.mzwise.modules.ucenter.entity.UcRecharge;
import com.mzwise.modules.ucenter.entity.UcTransfer;
import com.mzwise.modules.ucenter.entity.UcWithdraw;
import com.mzwise.modules.ucenter.vo.AdminAssetsRecordVO;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/01
 */
public interface AdminAssetService {
    /**
     * 后台查询充币记录
     *
     * @param nickname
     * @param phone
     * @param email
     * @param symbol
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<UcRecharge> listRechargeRecordSelective(String nickname, String phone, String email, String symbol, RechargeTypeEnum rechargeType, Date beginDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 后台查询提币记录
     *
     * @param nickname
     * @param phone
     * @param email
     * @param symbol
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<UcWithdraw> listWithdrawalRecordSelective(String nickname, String phone, String email, String symbol, Date beginDate, Date endDate, Boolean isCheck, Integer pageNum, Integer pageSize);

    /**
     * 后台查询划转记录
     *
     * @param nickname
     * @param phone
     * @param email
     * @param symbol
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<UcTransfer> listTransferRecordSelective(String nickname, String phone, String email, String symbol, Date beginDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 后台查询兑换记录
     *
     * @param nickname
     * @param phone
     * @param email
     * @param fromSymbol
     * @param toSymbol
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<UcExchange> listExchangeRecordSelective(String nickname, String phone, String email, String fromSymbol, String toSymbol, Date beginDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 后台查询资产记录
     *
     * @param nickname
     * @param phone
     * @param email
     * @param walletType
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminAssetsRecordVO> listAdminAssetsRecord(String nickname, String phone, String email, TransactionTypeEnum transactionType, WalletTypeEnum walletType, Date beginDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 出金审核
     *
     * @param request
     * @param id
     * @param isPass
     * @param refuseReason
     */
    void checkWithdraw(HttpServletRequest request, Long id, Boolean isPass, String refuseReason, Long adminId) throws MessagingException;


}
