package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.provider.EmailProvider;
import com.mzwise.constant.*;
import com.mzwise.modules.home.entity.HomeCoin;
import com.mzwise.modules.home.service.HomeCoinService;
import com.mzwise.modules.ucenter.entity.*;
import com.mzwise.modules.ucenter.mapper.UcTransactionMapper;
import com.mzwise.modules.ucenter.service.*;
import com.mzwise.modules.ucenter.vo.AdminAssetsRecordVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/01
 */
@Service
public class AdminAssetsServiceImpl implements AdminAssetService {
    @Autowired
    private UcRechargeService rechargeService;
    @Autowired
    private UcWithdrawService withdrawService;
    @Autowired
    private UcTransferService transferService;
    @Autowired
    private UcExchangeService exchangeService;
    @Autowired
    private UcTransactionMapper transactionMapper;
    @Autowired
    private UcWalletService walletService;
    @Autowired
    private BiPayService biPayService;
    @Autowired
    private HomeCoinService coinService;
    @Autowired
    private EmailProvider emailProvider;
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcStatisticsService statisticsService;

    @Override
    public Page<UcRecharge> listRechargeRecordSelective(String nickname, String phone, String email, String symbol, RechargeTypeEnum rechargeType, Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<UcRecharge> page = new Page<>();
        QueryWrapper<UcRecharge> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(nickname)) {
            wrapper.lambda().likeRight(UcRecharge::getNickname, nickname);
        }
        if (StringUtils.isNotBlank(phone)) {
            wrapper.lambda().likeRight(UcRecharge::getPhone, phone);
        }
        if (StringUtils.isNotBlank(email)) {
            wrapper.lambda().likeRight(UcRecharge::getEmail, email);
        }
        if (StringUtils.isNotBlank(symbol)) {
            wrapper.lambda().eq(UcRecharge::getSymbol, symbol);
        }
        if ( ! ObjectUtils.isEmpty(rechargeType)) {
            wrapper.lambda().eq(UcRecharge::getRechargeType, rechargeType);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(UcRecharge::getArrivalTime, beginDate, endDate);
        }
        wrapper.lambda().orderByDesc(UcRecharge::getCreateTime);
        return rechargeService.page(page, wrapper);
    }

    @Override
    public Page<UcWithdraw> listWithdrawalRecordSelective(String nickname, String phone, String email, String symbol, Date beginDate, Date endDate, Boolean isCheck, Integer pageNum, Integer pageSize) {
        Page<UcWithdraw> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcWithdraw> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(nickname)) {
            wrapper.lambda().likeRight(UcWithdraw::getNickname, nickname);
        }
        if (StringUtils.isNotBlank(phone)) {
            wrapper.lambda().likeRight(UcWithdraw::getPhone, phone);
        }
        if (StringUtils.isNotBlank(email)) {
            wrapper.lambda().likeRight(UcWithdraw::getEmail, email);
        }
        if (StringUtils.isNotBlank(symbol)) {
            wrapper.lambda().eq(UcWithdraw::getSymbol, symbol);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(UcWithdraw::getCreateTime, beginDate, endDate);
        }
        if (isCheck) {
            wrapper.lambda().eq(UcWithdraw::getStatus, WithdrawStatusEnum.WAITING);
        } else {
            wrapper.lambda().ne(UcWithdraw::getStatus, WithdrawStatusEnum.WAITING);
        }
        wrapper.lambda().orderByDesc(UcWithdraw::getCreateTime);
        return withdrawService.page(page, wrapper);
    }

    @Override
    public Page<UcTransfer> listTransferRecordSelective(String nickname, String phone, String email, String symbol, Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<UcTransfer> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcTransfer> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(nickname)) {
            wrapper.lambda().likeRight(UcTransfer::getNickname, nickname);
        }
        if (StringUtils.isNotBlank(phone)) {
            wrapper.lambda().likeRight(UcTransfer::getPhone, phone);
        }
        if (StringUtils.isNotBlank(email)) {
            wrapper.lambda().likeRight(UcTransfer::getEmail, email);
        }
        if (StringUtils.isNotBlank(symbol)) {
            wrapper.lambda().eq(UcTransfer::getSymbol, symbol);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(UcTransfer::getCreateTime, beginDate, endDate);
        }
        wrapper.lambda().orderByDesc(UcTransfer::getCreateTime);
        return transferService.page(page, wrapper);
    }

    @Override
    public Page<UcExchange> listExchangeRecordSelective(String nickname, String phone, String email, String fromSymbol, String toSymbol, Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<UcExchange> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcExchange> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(nickname)) {
            wrapper.lambda().likeRight(UcExchange::getNickname, nickname);
        }
        if (StringUtils.isNotBlank(phone)) {
            wrapper.lambda().likeRight(UcExchange::getPhone, phone);
        }
        if (StringUtils.isNotBlank(email)) {
            wrapper.lambda().likeRight(UcExchange::getEmail, email);
        }
        if (StringUtils.isNotBlank(fromSymbol)) {
            wrapper.lambda().eq(UcExchange::getFromSymbol, fromSymbol);
        }
        if (StringUtils.isNotBlank(toSymbol)) {
            wrapper.lambda().eq(UcExchange::getToSymbol, toSymbol);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(UcExchange::getCreateTime, beginDate, endDate);
        }
        wrapper.lambda().orderByDesc(UcExchange::getCreateTime);
        return exchangeService.page(page, wrapper);
    }

    @Override
    public Page<AdminAssetsRecordVO> listAdminAssetsRecord(String nickname, String phone, String email, TransactionTypeEnum transactionType, WalletTypeEnum walletType, Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<AdminAssetsRecordVO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcTransaction> wrapper = new QueryWrapper<>();
        wrapper.ge("ut.id", 1);
        if (walletType != null) {
            wrapper.lambda().eq(UcTransaction::getWalletType, walletType);
        }
        if (transactionType != null) {
            wrapper.lambda().eq(UcTransaction::getType, transactionType);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(UcTransaction::getCreateTime, beginDate, endDate);
        }
        return transactionMapper.listAssetsRecord(page, nickname, phone, email, wrapper);
    }

    @Override
    public void checkWithdraw(HttpServletRequest request, Long id, Boolean isPass, String refuseReason, Long adminId) throws MessagingException {
        UcWithdraw withdraw = withdrawService.getById(id);
        UcMember member = memberService.getById(withdraw.getMemberId());
        String areaCode = member.getAreaCode();
        Assert.notNull(member,"该用户不存在！");
        if (withdraw == null) {
            throw new ApiException("该出金记录不存在或已被审核过");
        }
        if (isPass) {
            walletService.platformCharges(withdraw.getFee());
            withdraw.setStatus(WithdrawStatusEnum.PASS);
//            HomeCoin coin = coinService.getById(withdraw.getSymbol());
//            String txid = biPayService.withdraw(request, withdraw.getId().toString(), withdraw.getAmount(), coin, withdraw.getAddress(), withdraw.getRemark());
            withdrawService.success(withdraw.getMemberId(), withdraw.getId(), null, withdraw.getAmount(), BigDecimal.ZERO, adminId);
            emailProvider.sendwithdrawStatus(member.getEmail(),withdraw.getStatus(),null,withdraw.getArrivedAmount(),areaCode);
            // 统计：增加提币(成功)数量
            statisticsService.addWithdrawSuccess(withdraw.getArrivedAmount());
        } else {
            withdrawService.fail(withdraw.getId(), adminId, refuseReason); //减少冻结资金 返还提币金额
            withdraw.setStatus(WithdrawStatusEnum.FAIL);
            withdraw.setRefuseReason(refuseReason);
            emailProvider.sendwithdrawStatus(member.getEmail(),withdraw.getStatus(),withdraw.getRefuseReason(),null,areaCode);
            withdrawService.updateById(withdraw);
        }
    }

}
