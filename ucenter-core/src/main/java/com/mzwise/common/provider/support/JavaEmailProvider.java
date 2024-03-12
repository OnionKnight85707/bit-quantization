package com.mzwise.common.provider.support;

import com.mzwise.common.api.CommonResult;
import com.mzwise.common.config.MailConfig;
import com.mzwise.common.provider.EmailProvider;
import com.mzwise.constant.WithdrawStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wmf
 */
@Slf4j
@Service
public class JavaEmailProvider implements EmailProvider {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailConfig mailConfig;

    @Override
    public CommonResult sendVerifyMessage(String email, String verifyCode,String areaCode) throws MessagingException {
        MimeMessage mimeMessage = mailConfig.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(this.mailConfig.getUsername());
        helper.setTo(email);
        String cnAreaCode="0086";
        if(!cnAreaCode.equals(areaCode)){
            helper.setSubject("Taurus");
            String text = "Your verification code is " + verifyCode + " ,If it is not operated by yourself, please ignore it.";
            helper.setText(text, false);
        }else {
            helper.setSubject("比特量化");
            String text = "您的驗證碼為" + verifyCode + ",如非本人操作，請忽略。";
            helper.setText(text, false);
        }

        //发送邮件
        javaMailSender.send(mimeMessage);
        return CommonResult.success();
    }

    @Override
    public CommonResult sendwithdrawStatus(String email, WithdrawStatusEnum status, String refuseReason, BigDecimal arrivedAmount,String areaCode) throws MessagingException {
        MimeMessage mimeMessage = mailConfig.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(this.mailConfig.getUsername());
        helper.setTo(email);
        String cnAreaCode="0086";
        if(!cnAreaCode.equals(areaCode)){
            helper.setSubject("Taurus");
            if (status.equals(WithdrawStatusEnum.FAIL)){
                String text = "The result of this currency withdrawal application is:（"+status+"）,The failure reason is："+refuseReason +".If it is not operated by yourself, please ignore it.";
                helper.setText(text,false);
            }
            if (status.equals(WithdrawStatusEnum.PASS)) {
                String text = "The result of this currency withdrawal application is（" + status + "），The estimated amount received is "+arrivedAmount.setScale(2, RoundingMode.HALF_UP)+" USDT,If it is not operated by yourself, please ignore it.";
                helper.setText(text,false);
            }
        }else {
            helper.setSubject("比特量化");
            if (status.equals(WithdrawStatusEnum.FAIL)){
                 String text = "本次提幣申請結果為:失敗（"+ status +"）,失敗原因為："+refuseReason +"。如非本人操作，請忽略。";
                helper.setText(text,false);
            }
            if (status.equals(WithdrawStatusEnum.PASS)) {
                  String text = "本次提幣申請結果為:通過（" + status + "），預計到賬金額為"+arrivedAmount.setScale(2, RoundingMode.HALF_UP)+"USDT,請及時查看。如非本人操作，請忽略。";
                helper.setText(text,false);
            }
        }
        javaMailSender.send(mimeMessage);
        return CommonResult.success();
    }


    @Override
    public CommonResult sendRemindTheBalanceIsInsufficient(String email,String areaCode) throws MessagingException {
        MimeMessage mimeMessage = mailConfig.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(this.mailConfig.getUsername());
        helper.setTo(email);
        String cnAreaCode="0086";
        if(!cnAreaCode.equals(areaCode)){
            helper.setSubject("Taurus");
            String text = "Hello, the system detects that your account balance is insufficient. If you need to continue to use the system, please recharge in time.If it is not operated by yourself, please ignore it.";
            helper.setText(text, false);
        }else{
            helper.setSubject("比特量化");
            String text = "您好，系統檢測到您的賬戶余額不足。如果您需要繼續使用該系統，請及時充值。如非本人操作，請忽略。";
            helper.setText(text, false);
        }
        //发送邮件
        javaMailSender.send(mimeMessage);
        return CommonResult.success();
    }
}
