package com.mzwise.common.provider.support;

import com.alibaba.fastjson.JSON;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.util.GeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 麦讯通短信
 *
 * @author: David Liang
 * @create: 2022-07-22 13:52
 */
@Component
@Slf4j
public class MaiXunTongSMSProvider {


    // 单发http地址
    @Value("${maixuntong.url_single_http}")
    private String urlSingleHttp;

    // 单发https地址
    @Value("${maixuntong.url_single_https}")
    private String urlSingleHttps;

    // 群发http地址
    @Value("${maixuntong.url_group_http}")
    private String urlGroupHttp;

    // 群发https地址
    @Value("${maixuntong.url_group_https}")
    private String urlGroupHttps;

    // 国内账号
    @Value("${maixuntong.account_internal}")
    private String accountInternal;

    // 国内密码
    @Value("${maixuntong.pwd_internal}")
    private String pwdInternal;

    // 国际账号
    @Value("${maixuntong.account_foreign}")
    private String accountForeign;

    // 国际密码
    @Value("${maixuntong.pwd_foreign}")
    private String pwdForeign;

    // 国内短信内容
    private String contentInternal = "【比特量化】您的短信验证码是:";

    // 国外短信内容
    private String contentForeign = "[Taurus]Your SMS verification code is:";

    // 中国区号
    private String areaCodeOfChina1 = "0086";
    private String areaCodeOfChina2 = "+86";

    // 区号前缀
    private static String areaCodePrefix1 = "00";
    private static String areaCodePrefix2 = "+";
    private static String areaCodePrefix2_split = "\\+";



    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;

    private static LocaleMessageDecorateSourceService messageService;

    @PostConstruct
    public void postConstruct() {
        messageService = localeMessageDecorateSourceService;
    }
    
    /**
     * 单发短信
     * 如果是国内的手机，需要用国内的账号密码发送，而且手机号不需要"+"号，也不需要带"86"区号
     * 如果是国外的手机，需要用国外的账号密码发送，而且手机号不需要"+"号，需要带区号(比如香港需要带852)
     * @param areaCode  区号
     * @param phoneNumber 手机号码
     * @param verifyCode  验证码
     * @return
     */
    public void sendSingle(String areaCode, String phoneNumber, String verifyCode) {
        if (StringUtils.isBlank(areaCode) || StringUtils.isBlank(phoneNumber) || StringUtils.isBlank(verifyCode)) {
            throw new ApiException(messageService.getSystemMessage(ExceptionCodeConstant.MaiXunTongSMSProvider_001));
        }
        String url = urlSingleHttp;
        String account;
        String pwd;
        String content;
        // 区号简写，00852 -> 852
        String areaCodeShort = filterAreaCode(areaCode);
        // 通道
        String passage;
        // 如果是国内的手机，需要用国内的账号密码，否则用国际账号密码
        if (areaCodeOfChina1.equals(areaCode) || areaCodeOfChina2.equals(areaCode)) {
            passage = "国内";
            account = accountInternal;
            pwd = pwdInternal;
            content = contentInternal + verifyCode;
        } else {
            passage = "国际";
            account = accountForeign;
            pwd = pwdForeign;
            content = contentForeign + verifyCode;
            phoneNumber = areaCodeShort + phoneNumber;
        }
        boolean needstatus = false; // 是否需要状态报告，需要true，不需要false
        String product = ""; // 产品ID
        String extno = ""; // 扩展码
        String respType = "json"; // 返回json格式响应
        boolean encrypt = true; // 密码使用时间戳加密
        try {
            log.info("麦讯通发送短信：通道={}, areaCode={}, phoneNumber={}", passage, areaCode, GeneratorUtil.phoneMasking(phoneNumber));
            String returnString = MXTHttpSender.send(url, account, pwd, phoneNumber, content, needstatus, product, extno, respType, encrypt);
            log.info("麦讯通短信响应：{}", JSON.toJSONString(returnString));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(messageService.getSystemMessage(ExceptionCodeConstant.MaiXunTongSMSProvider_002));
        }
    }

    /**
     * 获取正确的过滤手机号码
     * @param areaCode 区号
     * @param phoneNumber 手机号
     * @return
     */
    public static String getFilterPhoneNum(String areaCode, String phoneNumber) {
        // 区号简写，00852 -> 852
        String areaCodeShort = filterAreaCode(areaCode);
        phoneNumber = filterPhoneNumber(areaCode, phoneNumber);
        phoneNumber = filterPhoneNumberByAreaCodeShort(areaCodeShort, phoneNumber);
        return phoneNumber;
    }

    /**
     * 过滤区号
     * @param areaCode(只能是00开头)
     * @return
     */
    private static String filterAreaCode(String areaCode) {
        if (areaCode.startsWith(areaCodePrefix1)) {
            areaCode = cutAssemble(areaCode, areaCodePrefix1);
        } else if (areaCode.startsWith(areaCodePrefix2)) {
            areaCode = cutAssemble(areaCode, areaCodePrefix2_split);
        }
        return areaCode;
    }

    /**
     * 过滤手机号
     * @param areaCode 区号(只能是00开头)
     * @param phoneNumber 手机号
     * @return
     */
    private static String filterPhoneNumber(String areaCode, String phoneNumber) {
        // 把+号替换成00
        if (phoneNumber.startsWith(areaCodePrefix2)) {
            phoneNumber = areaCodePrefix1 + cutAssemble(phoneNumber, areaCodePrefix2_split);
        }
        if (phoneNumber.startsWith(areaCodePrefix1)) {
            if ( ! phoneNumber.startsWith(areaCode)) {
                log.error("麦讯通发送短信过滤手机号错误: areaCode={}, phoneNumber={}", areaCode, phoneNumber);
                throw new ApiException(messageService.getSystemMessage(ExceptionCodeConstant.MaiXunTongSMSProvider_003));
//                throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.MaiXunTongSMSProvider_003));
            }
            phoneNumber = cutAssemble(phoneNumber, areaCode);
        }
        return phoneNumber;
    }

    /**
     * 过滤手机号根据区号简写
     * @param areaCodeShort 区号简写
     * @param phoneNumber 手机
     * @return
     */
    private static String filterPhoneNumberByAreaCodeShort(String areaCodeShort, String phoneNumber) {
        if (phoneNumber.startsWith(areaCodeShort)) {
            phoneNumber = cutAssemble(phoneNumber, areaCodeShort);
        }
        return phoneNumber;
    }

    /**
     * 切割组装
     * @param targetStr 目标字符串
     * @param spiltStr 分割字符串
     * @return
     */
    private static String cutAssemble(String targetStr, String spiltStr) {
        String resultStr = "";
        String[] splitArr = targetStr.split(spiltStr);
        for (int i=1; i<splitArr.length; i++) {
            resultStr += splitArr[i];
        }
        return resultStr;
    }

//    public static void main(String[] args) {
//        String uri = "http://www.weiwebs.cn/msg/HttpSendSM";//应用地址
//        // 国际账号
////        String account = "";//账号
////        String pswd = "";//密码
//        // 国内账号
//        String account = "";//账号
//        String pswd = "";//密码
////        String mobiles = "85251330036";//手机号码，多个号码使用","分割
//        String mobiles = "8613713842175";//手机号码，多个号码使用","分割
////        String content = "[Taurus]Your SMS verification code is:" + 7221428;//短信内容
//        String content = "【比特量化】您的短信验证码是:" + 123456;//短信内容
//        boolean needstatus = false;//是否需要状态报告，需要true，不需要false
//        String product = "";//产品ID
//        String extno = "";//扩展码
//        String respType = "json";//返回json格式响应
//        boolean encrypt = true;// 密码使用时间戳加密
//        try {
//            String returnString = HttpSender.send(uri, account, pswd, mobiles, content, needstatus, product, extno, respType, encrypt);
//            System.out.println(returnString);
//            //TODO 处理返回值,参见HTTP协议文档
//        } catch (Exception e) {
//            //TODO 处理异常
//            e.printStackTrace();
//        }
//
////        String mobile = "85251338536";
////
////        boolean b = mobile.startsWith("85");
////        System.out.println(filterAreaCode("00852"));
////
////        System.out.println(filterPhoneNumber("00852", "00852123456"));
//
//    }

}
