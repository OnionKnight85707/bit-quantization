package com.mzwise.modules.ucenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.annotation.CustomVerifyRequired;
import com.mzwise.annotation.PayPasswordRequired;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.api.ResultCode;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.util.*;
import com.mzwise.constant.PartnerLevelEnum;
import com.mzwise.modules.common.service.CodeCacheService;
import com.mzwise.modules.ucenter.dto.*;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcMemberAppend;
import com.mzwise.modules.ucenter.service.*;
import com.mzwise.modules.ucenter.service.impl.GoogleAuthenticatorService;
import com.mzwise.modules.ucenter.vo.AreaCodeVO;
import com.mzwise.modules.ucenter.vo.MemberSimpleVO;
import com.mzwise.modules.ucenter.vo.PartnerCommissionCalcVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-01-07
 */
@Api(tags = "会员管理")
@RestController
@Slf4j
@RequestMapping("/uc/member")
public class UcMemberController {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${encryption.des.gg}")
    private String desKey;

    @Value("${defaultAvatar.url}")
    private String url;

    @Value("${defaultAvatar.picPath}")
    private String picPath;

    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcenterMemberService ucenterMemberService;
    @Autowired
    private CodeCacheService codeCacheService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UcInviteRecordService inviteRecordService;
    @Autowired
    private GoogleAuthenticatorService googleAuthenticatorService;
    @Autowired
    private UcMemberAppendService ucMemberAppendService;
    @Autowired
    private UcAreaCodeService ucAreaCodeService;
    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;
    @Autowired
    private UcPartnerLevelService partnerLevelService;
    @Autowired
    private UcPartnerLevelSerivce ucPartnerLevelSerivce;

    @ApiOperation(value = "会员注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @AnonymousAccess
    public CommonResult register(@Validated @RequestBody UcMemberRegisterParam params, HttpServletRequest request) {
        String loginPassword = params.getPassword();
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\W]{8,40}$";
        if(!Pattern.matches(regex, params.getPassword())) {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_001));
        }
        Assert.isTrue(!StringUtils.isEmpty(params.getPhone()) || !StringUtils.isEmpty(params.getEmail()), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.HomeQuestionnaireController_001));
        String account = !StringUtils.isEmpty(params.getPhone()) ? params.getPhone() : params.getEmail();
        String code = codeCacheService.get(account);
        Assert.isTrue(params.getCode().equals(code), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_002));
        codeCacheService.del(account);
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(params.getPassword());
        params.setPassword(encodePassword);
        UcMember member = memberService.register(params);
        if (member == null) {
            return CommonResult.failed();
        }
        member.setPromotionCode(RandomStringUtils.randomAlphanumeric(6));
        if (!StringUtils.isEmpty(params.getPromotionCode())) {
            UcMember supMember = memberService.queryByPromotionCode(params.getPromotionCode());
            if (supMember != null) {
                member.setParentId(supMember.getId());
                member.setParentNickname(supMember.getNickname());
                inviteRecordService.create(member.getId(), supMember.getId());
                supMember.setFirstLevelNum(supMember.getFirstLevelNum() + 1);
                memberService.updateById(member);
                memberService.updateAndDelCache(supMember);
                ucPartnerLevelSerivce.checkIfRecommender(supMember.getId());
            }
        }

        // 此处实现注册之后完成登录
        String token = ucenterMemberService.login(params.getEmail(), loginPassword, request);
        if (token == null) {
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_003));
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        memberService.updateAndDelCache(member);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "手机号登录")
    @RequestMapping(value = "/loginByPhone", method = RequestMethod.POST)
    @AnonymousAccess
    public CommonResult login(@Validated @RequestBody UcMemberLoginByPhoneParam params) {
        String code = codeCacheService.get(params.getPhone());
        Assert.isTrue(params.getCode().equals(code), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_002));
        String token = ucenterMemberService.login(params.getPhone());
        if (token == null) {
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_003));
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "邮箱登录")
    @RequestMapping(value = "/loginByEmail", method = RequestMethod.POST)
    @AnonymousAccess
    public CommonResult login(@Validated @RequestBody UcMemberLoginByEmailParam params) {
        String code = codeCacheService.get(params.getEmail());
        Assert.isTrue(params.getCode().equals(code), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_002));
        String token = ucenterMemberService.login(params.getEmail());
        if (token == null) {
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_003));
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "绑定邮箱")
    @RequestMapping(value = "/bind/email", method = RequestMethod.POST)
    public CommonResult bindEmail(@Validated @RequestBody BindEmailParam param) {
        String code = codeCacheService.get(param.getEmail());
        Assert.isTrue(param.getCode().equals(code), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_002));
        memberService.updateEmail(SecurityUtils.getCurrentUserId(), param.getEmail());
        return CommonResult.success();
    }

    @ApiOperation(value = "绑定手机")
    @PayPasswordRequired
    @RequestMapping(value = "/bind/phone", method = RequestMethod.POST)
    public CommonResult bindEmail(@Validated @RequestBody BindPhoneParam param) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        String code = codeCacheService.get(param.getPhone(), currentUserId);
        Assert.isTrue(param.getCode().equals(code), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_002));
        memberService.updatePhone(SecurityUtils.getCurrentUserId(), param.getAreaCode(), param.getPhone());
        return CommonResult.success();
    }


    @ApiOperation(value = "开启手机验证")
    @RequestMapping(value = "/open/phone/verify", method = RequestMethod.POST)
    @CustomVerifyRequired
    public CommonResult openPhoneVerify(@Validated @RequestBody UpdatePhoneVerifyParam param) {
        memberService.updatePhoneVerify(SecurityUtils.getCurrentUserId(), param.getPhoneVerify());
        return CommonResult.success();
    }

    @ApiOperation(value = "开启邮箱验证")
    @RequestMapping(value = "/open/email/verify", method = RequestMethod.POST)
    @CustomVerifyRequired
    public CommonResult openEmailVerify(@Validated @RequestBody UpdateEmailVerifyParam param) {
        memberService.updateEmailVerify(SecurityUtils.getCurrentUserId(), param.getEmailVerify());
        return CommonResult.success();
    }

    @ApiOperation(value = "账号密码登录")
    @RequestMapping(value = "/loginByPassword", method = RequestMethod.POST)
    @AnonymousAccess
    public CommonResult login(@Validated @RequestBody UcMemberLoginParam params, HttpServletRequest request) {
        String ip=IpUtils.getIP(request);
        log.info("用户尝试登录 ,user={} ,ip={}",params.getUsername(),ip);
        String token = ucenterMemberService.login(params.getUsername(), params.getPassword(), request);
        if (token == null) {
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_003));
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "用户详情")
    @GetMapping(value = "/detail")
    public CommonResult<UcMember> detail() {
        Long id = SecurityUtils.getCurrentUserId();
        UcMember currentUser = memberService.getById(id);
        currentUser.setPassword(null);
        currentUser.setPayPassword(null);
        if ( ! ObjectUtils.isEmpty(currentUser.getPartnerLevelId())) {
            PartnerLevelEnum partnerLevel = PartnerLevelEnum.getPartnerLevelEnum(currentUser.getPartnerLevelId());
            currentUser.setPartnerLevel(partnerLevel);
        }
        return CommonResult.success(currentUser);
    }

    @ApiOperation(value = "用户登出")
    @PostMapping(value = "/logout")
    public CommonResult logout() {
        Long id = SecurityUtils.getCurrentUserId();

        memberService.logout(id);

        return CommonResult.success();
    }


//    @ApiOperation(value = "用户详情")
//    @GetMapping(value = "/detail/{id}")
//    @AnonymousAccess
//    public CommonResult<MemberSimpleVO> detail(@PathVariable Long id) {
//        UcMember currentUser = memberService.getById(id);
//        MemberSimpleVO vo = new MemberSimpleVO();
//        BeanUtils.copyProperties(currentUser, vo);
//        return CommonResult.success(vo);
//    }

    @ApiOperation(value = "忘记密码")
    @PutMapping(value = "/forgetPassword")
    @AnonymousAccess
    public CommonResult<Object> forgetPassword(@Validated @RequestBody UcMemberResetPasswordParam params) {
        log.info(" 用户忘记密码，通过邮件修改登录密码: user={}",params.getAccount());
        UcMember member = memberService.getMemberByUsername(params.getAccount());
        Assert.isTrue(member != null, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_004));
        String code = codeCacheService.get(params.getAccount());
        Assert.isTrue(params.getCode().equals(code), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_002));
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(params.getPassword());
        params.setPassword(encodePassword);
        memberService.resetPassword(params);
        codeCacheService.del(params.getAccount());
        return CommonResult.success("reset_success");
    }

//    @ApiOperation("修改头像（已作废，当前调用默认头像）")
//    @PutMapping("/updateAvatar")
//    public CommonResult updateAvatar(@RequestBody String url) {
//        Long currentUserId = SecurityUtils.getCurrentUserId();
//        memberService.updateAvatar(currentUserId, url);
//        return CommonResult.success();
//    }

    @ApiOperation("修改昵称")
    @PutMapping("/updateNickname")
    public CommonResult updateNickname(@RequestBody String nickname) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        memberService.updateNickname(currentUserId, nickname);
        return CommonResult.success();
    }

    @ApiOperation("修改密码")
    @PutMapping("/updatePassword")
    public CommonResult updatePassword(@RequestBody UcUpdatePasswordParam ucUpdatePasswordParam) {
        UcMember member = ucenterMemberService.getById(SecurityUtils.getCurrentUserId());
        log.info("用户尝试修改密码。。。userId={}",member.getId());
        Assert.hasText(ucUpdatePasswordParam.getOldPassword(),localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_005));
        Assert.hasText(ucUpdatePasswordParam.getNewPassword(), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_006));
        if (!passwordEncoder.matches(ucUpdatePasswordParam.getOldPassword(),member.getPassword())) {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_007));
        }
        String reg="^(?![A-Za-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{8,16}$";
        if(!Pattern.matches(reg,ucUpdatePasswordParam.getNewPassword())){
            throw new ApiException("新密码必须符合8位数以上数字字母组合");
        }
        ucUpdatePasswordParam.setNewPassword(passwordEncoder.encode(ucUpdatePasswordParam.getNewPassword()));
        memberService.updatePassword(member.getId(), ucUpdatePasswordParam);

        return CommonResult.success();
    }

    @ApiOperation("设置资金密码")
    @PostMapping("/setPayPassword")
    public CommonResult setPayPassword(@RequestBody String payPasswordJson) {
        JSONObject jsonObject = JSONObject.parseObject(payPasswordJson);
        String payPassword = jsonObject.getString("payPassword");
        UcMember currentUser = SecurityUtils.getCurrentUser();
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(payPassword);
        memberService.setPayPassword(currentUser.getId(), encodePassword);
        return CommonResult.success();
    }

    @ApiOperation("修改资金密码")
    @PutMapping("/updatePayPassword")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult updateTransaction(@RequestBody UcUpdatePasswordParam ucUpdatePasswordParam) {
        Assert.hasText(ucUpdatePasswordParam.getOldPassword(), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_009));
        Assert.hasText(ucUpdatePasswordParam.getNewPassword(), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_010));
        Assert.isTrue(ucUpdatePasswordParam.getNewPassword().length() == 6, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_011));
        Long currentUserId = SecurityUtils.getCurrentUserId();
        UcMember member = memberService.getById(currentUserId);
        Assert.isTrue(passwordEncoder.matches(ucUpdatePasswordParam.getOldPassword(), member.getPayPassword()), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_012));
        String encodePassword = passwordEncoder.encode(ucUpdatePasswordParam.getNewPassword());
        memberService.setPayPassword(currentUserId, encodePassword);
        return CommonResult.success();
    }

    @ApiOperation("忘记资金密码")
    @PostMapping("/forgetFundPassword")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult forgetFundPassword(@RequestBody UcForgetPasswordParam ucForgetPasswordParam) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        UcMember member = memberService.getById(currentUserId);
        if (!member.getIsSetPayPassword() || member.getPayPassword() == null) {
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_013));
        }
        //验证邮箱验证码
        String emailCode = codeCacheService.get(member.getEmail());
        Assert.isTrue(ucForgetPasswordParam.getInputCode().equals(emailCode), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_002));
        Assert.hasText(ucForgetPasswordParam.getNewPassword(), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_010));
        Assert.isTrue(ucForgetPasswordParam.getNewPassword().length() == 6,localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_011));
        String encodePassword = passwordEncoder.encode(ucForgetPasswordParam.getNewPassword());
        memberService.setPayPassword(currentUserId, encodePassword);
        return CommonResult.success();
    }

    @ApiOperation(value = "修改语言", notes = "该接口只影响系统主动给用户发送消息时使用的语言，" +
            "每个接口调用返回的语言还是根据header中的lang判断, zh_CN 中文 en_US 英语")
    @PutMapping("/local/{lang}")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult updateLocal(@PathVariable("lang") String lang) {
        memberService.setLang(SecurityUtils.getCurrentUserId(), lang);
        return CommonResult.success();
    }


//    @ApiOperation("合伙人数据回显")
//    @GetMapping("/ShowPartnerData")
//    public CommonResult<ShowPartnerParam> ShowPartnerData(@RequestParam @ApiParam Long id){
//        UcMember ucMember = memberService.getById(id);
//        ShowPartnerParam showPartnerParam = new ShowPartnerParam();
//        showPartnerParam.setIsPartner(ucMember.getIsPartner());
//        showPartnerParam.setPartnerCommissionRate(ucMember.getPartnerCommissionRate());
//        return CommonResult.success(showPartnerParam);
//    }


    /**
     * 修改合伙人
     * @param ucenterPartnerParam
     * @return
     */
    @ApiOperation("修改合伙人")
    @PostMapping("/updatePartner")
    public CommonResult<UcMember> updatePartner(@RequestBody  @Validated UcenterPartnerParam ucenterPartnerParam) {
        UcMember ucMember = new UcMember();
        Long currentUserId = SecurityUtils.getCurrentUserId();
        UcMember subMember = memberService.getById(ucenterPartnerParam.getId());
        if (currentUserId == null || subMember.getParentId() == null || ! subMember.getParentId().equals(currentUserId)) {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_014));
        }
        if (subMember.getIsPartner()){
            // 如果将下级是合伙人，则不能修改成非合伙人
            if (!ucenterPartnerParam.getIsPartner()){
                throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_021));
            }
        }
        if (ucenterPartnerParam.getIsPartner()) {
            CommonResult checkResult = ucenterMemberService.checkParentCommissionRate(ucenterPartnerParam, currentUserId);
            if (!checkResult.isSuccess()) {
                return checkResult;
            }
        }
        if (ucenterPartnerParam.getPartnerCommissionRate().compareTo(new BigDecimal("0.3"))>0)
        {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcenterMemberServiceImpl_004));
        }
        BeanUtils.copyProperties(ucenterPartnerParam, ucMember);
        ucMember.setPartnerLevelId(PartnerLevelEnum.SILVER.getValue());
        ucenterMemberService.updateById(ucMember);
        return CommonResult.success();
    }

//    @GetMapping("/testPartnerCommission")
//    public CommonResult testPartnerCommission(@RequestParam Long currentMemberId, @RequestParam String orderId, @RequestParam BigDecimal deductionAward,
//                                              @RequestParam BigDecimal calcProfit, @RequestParam BigDecimal originProfit) {
//        memberService.handlePartnerCommission(true, currentMemberId, orderId, deductionAward, calcProfit, originProfit);
//        return CommonResult.success();
//    }
//
//    @GetMapping("/testInternationalization")
//    public CommonResult testInternationalization(@RequestParam String code) {
//        String partnerMaxRadio = localeMessageDecorateSourceService.getSystemMessage(code);
//        return CommonResult.success(partnerMaxRadio);
//    }
//
//    @GetMapping("/testGetPartnerCommissionRate")
//    @AnonymousAccess
//    public CommonResult testGetPartnerCommissionRate(@RequestParam Long memberId, @RequestParam Long businessDirectParentId, @RequestParam BigDecimal lastSetPartnerCommissionRate) {
//        while (true) {
//            PartnerCommissionCalcVo vo = partnerLevelService.getPartnerCommissionRate(memberId, businessDirectParentId, lastSetPartnerCommissionRate);
//            System.out.println("当前用户：" + memberId + ", parentId = " + vo.getParentId() + ", setRate = " + vo.getSetRate() + ", calcRate = " + vo.getCalcRate());
//            memberId = vo.getParentId();
//            lastSetPartnerCommissionRate = vo.getSetRate();
//            if (vo.getParentId().longValue() == 0) {
//                System.out.println("------------");
//                break;
//            }
//        }
//        return CommonResult.success();
//    }

    @ApiOperation("检查用户是否绑定谷歌私钥")
    @GetMapping("/checkIsBindGoogleAuthenticator")
    public CommonResult checkIsBindGoogleAuthenticator(){
        Long memberId = SecurityUtils.getCurrentUserId();
        UcMember ucMember = ucenterMemberService.getById(memberId);
        UcMemberAppend append = ucMemberAppendService.getByMemberId(memberId);
        if(!ucMember.getIsBindGoogleAuthenticator()&& append==null){
            GoogleToken generate = googleAuthenticatorService.generate();
            String QRCodeAddress = ucenterMemberService.cratePrivateKeyQRCode(memberId,generate.getPrivateKey());
            generate.setQRCodeAddress(QRCodeAddress);
            return CommonResult.success(generate,"Unbound");
        }
        return CommonResult.failed(ResultCode.IGNORED,localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_016));
    }


    /**
     *  绑定谷歌验证器私钥
     * @param bindGoogleParam
     * @return
     * @throws Exception
     */
    @ApiOperation("绑定谷歌验证器私钥")
    @PayPasswordRequired
    @PostMapping("/bindPrivateKey")
    public CommonResult bindPrivateKey(@RequestBody BindGoogleParam bindGoogleParam) throws Exception {
        Long memberId = SecurityUtils.getCurrentUserId();
        boolean isSuccess = googleAuthenticatorService.verifyPrivateKey(bindGoogleParam.getGoogleKey(), bindGoogleParam.getCode());
        UcMember member = ucenterMemberService.getById(memberId);
        Assert.notNull(member,localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_017));
        if(!isSuccess){
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_018));
        }
        UcMemberAppend append =new UcMemberAppend();
        append.setMemberId(memberId);
        DESTool tool=new DESTool(desKey);
        String encodeGoogleKey = tool.encode(bindGoogleParam.getGoogleKey());
        append.setGoogleKey(encodeGoogleKey);
        ucMemberAppendService.save(append);
        member.setIsBindGoogleAuthenticator(true);
        ucenterMemberService.updateById(member);
        return CommonResult.success("success！");
    }

    @ApiOperation("检查谷歌验证码输入是否正确")
    @GetMapping("/checkGoogleCode")
    public CommonResult checkGoogleCode(@RequestParam String code) throws Exception {
        Long memberId = SecurityUtils.getCurrentUserId();
        UcMemberAppend append = ucMemberAppendService.getByMemberId(memberId);
        String googleKey = append.getGoogleKey();
        DESTool tool = new DESTool(desKey);
        String decodeGoogleKey = tool.decode(googleKey);
        boolean isTrue = googleAuthenticatorService.verifyPrivateKey(decodeGoogleKey, code);
        if(!isTrue){
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_019));
        }
        return CommonResult.success();

    }


    @ApiOperation("取消绑定币安验证器")
    @PutMapping("/unbindGoogleAuthenticator")
    public CommonResult unbindGoogleAuthenticator(@RequestParam String inputCode){
        Long memberId = SecurityUtils.getCurrentUserId();
        UcMember member = ucenterMemberService.getById(memberId);
        UcMemberAppend append = ucMemberAppendService.getByMemberId(memberId);
        if(!member.getIsBindGoogleAuthenticator() || append==null){
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_015));
        }
        //验证邮箱验证码
        String emailCode = codeCacheService.get(member.getEmail());
        Assert.isTrue(inputCode.equals(emailCode),localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_002));
        member.setIsBindGoogleAuthenticator(false);
        memberService.updateById(member);
        Boolean isSuccess = ucMemberAppendService.deleteByMemberId(memberId);
        if(!isSuccess){
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_020));
        }
        return CommonResult.success();
    }

    @ApiOperation("检查用户是否绑定手机号码")
    @GetMapping("/checkIsBindPhone")
    public CommonResult checkIsBindPhone(){
        UcMember member = SecurityUtils.getCurrentUser();
        Boolean phoneVerify = member.getPhoneVerify();
        String phone = member.getPhone();
        if (!phoneVerify || phone==null){
            return CommonResult.failed(ResultCode.IGNORED,"用户未绑定手机号");
        }
        return CommonResult.success();
    }

    @ApiOperation("检查手机验证码输入是否正确")
    @PostMapping("/checkPhoneCode")
    public CommonResult checkPhoneCode(@RequestParam String inputCode){
        UcMember member = SecurityUtils.getCurrentUser();
        String code=codeCacheService.get(member.getPhone());
        Assert.isTrue(inputCode.equals(code),localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_002));
        return CommonResult.success();
    }

    @ApiOperation("查询全部默认头像图片")
    @GetMapping("/queryAllAvatar")
    public CommonResult<List<String>> queryAllAvatar() {
        List<String> list = new ArrayList<>();
        list.add(url + picPath + "1.jpg");
        list.add(url + picPath + "2.jpg");
        list.add(url + picPath + "3.jpg");
        list.add(url + picPath + "4.jpg");
        list.add(url + picPath + "5.jpg");
        list.add(url + picPath + "6.jpg");
        list.add(url + picPath + "7.jpg");
        list.add(url + picPath + "8.jpg");
        list.add(url + picPath + "9.jpg");
        list.add(url + picPath + "10.jpg");
        list.add(url + picPath + "11.jpg");
        list.add(url + picPath + "12.jpg");
        return CommonResult.success(list);
    }

    @ApiOperation("修改默认头像")
    @PostMapping("/modifyAvatar")
    public CommonResult modifyPic(@RequestParam String picPath) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        UcMember member = memberService.getById(currentUserId);
        member.setAvatar(picPath);
        memberService.updateById(member);
        return CommonResult.success();
    }

    @ApiOperation("返回所有区号")
    @GetMapping("/getAreaCode")
    public CommonResult<List<AreaCodeVO>> getAreaCode(){
        String language= LocaleSourceUtil.getLanguage();
        List<AreaCodeVO> list = ucAreaCodeService.areaCodelist(language);
        return CommonResult.success(list);
    }

//    @ApiOperation("获取滑动验证码")
//    @PostMapping({"/getCaptcha"})
//    @AnonymousAccess
//    public ResponseModel getCaptcha(@RequestBody CaptchaVO captchaVO) {
//        return captchaService.get(captchaVO);
//    }
//
//    @ApiOperation("校验滑动验证码")
//    @PostMapping({"/checkCaptcha"})
//    @AnonymousAccess
//    public ResponseModel checkCaptcha(@RequestBody CaptchaVO captchaVO) {
//        return captchaService.check(captchaVO);
//    }

}

