package com.mzwise.common.provider;

import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.WithdrawStatusEnum;

import javax.mail.MessagingException;
import java.math.BigDecimal;

public interface EmailProvider {

    /**
     * 发送验证码邮件
     *
     * @param email     邮箱
     * @param verifyCode 验证码
     * @return
     */
    CommonResult sendVerifyMessage(String email, String verifyCode,String areaCode) throws MessagingException;


    /**
     *   邮箱通知提币审核结果
     *
     * @param email
     * @param status
     * @param refuseReason
     * @param arrivedAmount
     * @return
     * @throws MessagingException
     */
    CommonResult sendwithdrawStatus(String email, WithdrawStatusEnum status, String refuseReason, BigDecimal arrivedAmount,String areaCode) throws  MessagingException;

    /**
     *   邮箱通知用户服务费余额不足
     * @param email
     * @return
     * @throws MessagingException
     */
    CommonResult sendRemindTheBalanceIsInsufficient(String email,String areaCode) throws MessagingException;
}
