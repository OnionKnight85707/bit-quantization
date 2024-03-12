package com.mzwise.common.provider.support;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.provider.SMSProvider;
import com.mzwise.common.util.LocaleSourceUtil;
import com.mzwise.modules.common.service.CodeCacheService;
import com.mzwise.modules.ucenter.entity.UcAreaCode;
import com.mzwise.modules.ucenter.service.UcAreaCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * 阿里云短信接口（https://help.aliyun.com/document_detail/102715.html?spm=a2c4g.11186623.2.9.17af5f30Hs6anZ#concept-t4w-pcs-ggb）
 * @author wmf
 */
@Slf4j
public class AliYunSMSProvider implements SMSProvider {
    private String username;
    private String password;
    private String sign;
    private String gateway;
    private DefaultProfile profile;

    @Autowired
    private AwsSMSProvider awsSMSProvider;
    @Autowired
    private MaiXunTongSMSProvider maiXunTongSMSProvider;
    @Autowired
    private UcAreaCodeService areaCodeService;
    @Autowired
    private CodeCacheService codeCacheService;
    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;

    public static String getName() {
        return "aliyun";
    }

    public AliYunSMSProvider(String gateway, String username, String password, String sign) {
        this.username = username;
        this.password = password;
        this.sign = sign;
        this.gateway = gateway;
        this.profile = DefaultProfile.getProfile(this.username, this.password, this.sign);
    }

    @Override
    public CommonResult sendVerifyMessage(String mobile, String verifyCode) throws Exception {
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(this.gateway);
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", this.username);
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "美付");
        request.putQueryParameter("TemplateCode", "SMS_198900173");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + verifyCode + "\"}");
        CommonResponse response = client.getCommonResponse(request);
        String data = response.getData();
        log.info(data);
        return CommonResult.success(null, "send_success");
    }

    /**
     * 发送亚马逊短信
     * @param mobile
     * @param verifyCode
     * @return
     * @throws Exception
     */
    @Override
    public CommonResult sendAwsMessage(String mobile, String verifyCode) throws Exception {
        boolean publishResult = awsSMSProvider.send2SNS(mobile, verifyCode);
        log.info("发送亚马逊短信结果：" + publishResult);
        return CommonResult.success(null, "send_success");
    }

    /**
     * 发送麦讯通短信
     * @param memberId 用户id
     * @param areaCode 区号
     * @param mobile 手机号
     * @param verifyCode 验证码
     * @return
     * @throws Exception
     */
    @Override
    public String sendMaiXunTongMessage(Long memberId, String areaCode, String mobile, String verifyCode) {
        String language= LocaleSourceUtil.getLanguage();
        UcAreaCode ucAreaCode = areaCodeService.findByAreaCode(areaCode,language);
        if (ObjectUtils.isEmpty(ucAreaCode)) {
  //          throw new ApiException("地区码错误");
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.AliYunSMSProvider_001));
        }
        // 返回正确过滤的手机号
        String filterPhoneNum = MaiXunTongSMSProvider.getFilterPhoneNum(areaCode, mobile);

        String redisCode = codeCacheService.get(filterPhoneNum, memberId);
        if (StringUtils.isNotBlank(redisCode)) {
     //       throw new ApiException("验证码获取频繁，请稍后再试");
           throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.AliYunSMSProvider_002));
        }

        codeCacheService.set(filterPhoneNum, memberId, verifyCode);
        maiXunTongSMSProvider.sendSingle(areaCode, filterPhoneNum, verifyCode);
        return filterPhoneNum;
    }

    @Override
    public CommonResult sendSingleMessage(String mobile, String content) throws Exception {
        return null;
    }

    @Override
    public CommonResult sendMessageByTempId(String mobile, String content, String templateId) throws Exception {
        return null;
    }

    @Override
    public CommonResult sendCustomMessage(String mobile, String content) throws Exception {
        return null;
    }
}
