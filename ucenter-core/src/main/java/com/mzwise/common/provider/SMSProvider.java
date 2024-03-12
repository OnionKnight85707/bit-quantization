package com.mzwise.common.provider;

import com.mzwise.common.api.CommonResult;

public interface SMSProvider {
    /**
     * 发送单条短信
     *
     * @param mobile  手机号
     * @param content 短信内容
     * @return
     * @throws Exception
     */
    CommonResult sendSingleMessage(String mobile, String content) throws Exception;

    /**
     * 发送单条短信
     *
     * @param mobile  手机号
     * @param content 短信内容
     * @return
     * @throws Exception
     */
    CommonResult sendMessageByTempId(String mobile, String content, String templateId) throws Exception;

    /**
     * 发送自定义短信
     *
     * @param mobile
     * @param content
     * @return
     * @throws Exception
     */
    CommonResult sendCustomMessage(String mobile, String content) throws Exception;

    /**
     * 发送验证码短信
     *
     * @param mobile     手机号
     * @param verifyCode 验证码
     * @return
     * @throws Exception
     */
    default CommonResult sendVerifyMessage(String mobile, String verifyCode) throws Exception {
        return sendSingleMessage(mobile, formatVerifyCode(verifyCode));
    }

    /**
     * 发送亚马逊短信
     * @param mobile
     * @param verifyCode
     * @return
     * @throws Exception
     */
    CommonResult sendAwsMessage(String mobile, String verifyCode) throws Exception;

    /**
     * 发送麦讯通短信
     * @param memberId 用户id
     * @param areaCode 区号
     * @param mobile 手机号
     * @param verifyCode 验证码
     * @return
     * @throws Exception
     */
    String sendMaiXunTongMessage(Long memberId, String areaCode, String mobile, String verifyCode) throws Exception;

    /**
     * 获取验证码信息格式
     *
     * @param code
     * @return
     */
    default String formatVerifyCode(String code) {
        return String.format("%s", code);
    }

    /**
     * 发送国际短信
     *
     * @param content
     * @param phone
     * @return
     */
    default CommonResult sendInternationalMessage(String content, String phone) throws Exception {
        return null;
    }
}
