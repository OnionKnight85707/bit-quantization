package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.util.DESTool;
import com.mzwise.common.util.GoogleToken;
import com.mzwise.modules.common.service.LoginLockService;
import com.mzwise.modules.ucenter.dto.ShowPartnerParam;
import com.mzwise.modules.ucenter.dto.UcMemberRegisterParam;
import com.mzwise.modules.ucenter.dto.UcenterPartnerParam;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcMemberAppend;
import com.mzwise.modules.ucenter.mapper.UcMemberMapper;
import com.mzwise.modules.ucenter.service.DictionaryService;
import com.mzwise.modules.ucenter.service.UcMemberAppendService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcenterMemberService;
import com.mzwise.security.component.TokenStorage;
import com.mzwise.security.util.JwtTokenUtil;
import com.mzwise.user.LoginDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Service
@Slf4j
public class UcenterMemberServiceImpl extends ServiceImpl<UcMemberMapper,UcMember> implements UcenterMemberService {


    //合伙人最小佣金比例
    private final static BigDecimal minPartnerCommissionRate = new BigDecimal("0.00001");

    @Value("${encryption.des.gg}")
    private String desKey;

    @Autowired
    private UcMemberService memberService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginLockService loginLockService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private GoogleAuthenticatorService googleAuthenticatorService;

    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;

    @Override
    public UcMember register(UcMemberRegisterParam param) {
        return null;
    }

    @Autowired
    private TokenStorage tokenStorage;


    @Override
    public UserDetails loadUserById(String id) {
        //获取用户信息
        UcMember member = memberService.getMemberById(Long.parseLong(id));
        if (member != null) {
            return new LoginDetail(member);
        }
        throw new UsernameNotFoundException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_003));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        UcMember member = memberService.getMemberByUsername(username);
        if (member != null) {
            return new LoginDetail(member);
        }
        throw new UsernameNotFoundException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_003));
    }

    @Override
    public String login(String account) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(account);
            Assert.isTrue(userDetails != null, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_003));
            Assert.isTrue(userDetails.isEnabled(), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcenterMemberServiceImpl_001));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public String login(String username, String password, HttpServletRequest request) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (userDetails==null) {
                throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberController_004));
            }
            if (loginLockService.isLocked(username)) {
                throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcenterMemberServiceImpl_003));
            }


            try {
                googleAuthenticatorService.verify(password);
            } catch (Exception e) {
                if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                    loginLockService.wrong(username);
                    throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcenterMemberServiceImpl_003));
                }

            }



            Assert.isTrue(userDetails.isEnabled(), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcenterMemberServiceImpl_001));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);

            tokenStorage.addTokenMap(username,token);

            // 更新用户登录信息
            memberService.updateMemberLoginInfo(request, Long.valueOf(userDetails.getUsername()));
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 查看上级返佣额度
     * @param param
     * @return
     */
    @Override
    public CommonResult checkParentCommissionRate(UcenterPartnerParam param, Long parentId) {
        DecimalFormat df=new DecimalFormat("0.00%");
        if (param.getPartnerCommissionRate().compareTo(dictionaryService.getPartnerMaxCommissionRatio()) == 1){
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcenterMemberServiceImpl_004));
        }
        UcMember currentMember = memberService.getById(parentId);
        if (currentMember.getIsPartner() == false) {
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcenterMemberServiceImpl_005));
        }
        BigDecimal currentRate = param.getPartnerCommissionRate();
        if (minPartnerCommissionRate.compareTo(currentRate) == 1) {
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcenterMemberServiceImpl_006));
        }
//        if (currentMember.getParentId() != null && currentMember.getParentId() != 0){
//            UcMember parent = memberService.getById(currentMember.getParentId());
            if(currentRate.compareTo(currentMember.getPartnerCommissionRate())==1){
                return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcenterMemberServiceImpl_008)+df.format(currentMember.getPartnerCommissionRate()));
            }
//        }

        BigDecimal subMaxCommissionRate = memberService.subMaxCommissionRate(param.getId());
        if (subMaxCommissionRate!=null && currentRate.compareTo(subMaxCommissionRate)<0){
            return CommonResult.failed(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcenterMemberServiceImpl_009)+df.format(subMaxCommissionRate)+localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcenterMemberServiceImpl_010));
        }
        return CommonResult.success();
    }


    /**
     *  检查用户输入的验证码是否正确
     * @param privateKey
     * @param authCode
     * @return
     */
    @Override
    public Boolean checkInputGoogleCode(String privateKey, String authCode) throws Exception {
        DESTool tool = new DESTool(desKey);
        String decodeKey = tool.decode(privateKey);
        return googleAuthenticatorService.verifyPrivateKey(decodeKey,authCode);
    }


    /**
     *  给用户生成谷歌验证器私钥二维码
     * @return
     */
    @Override
    public String cratePrivateKeyQRCode(Long id,String privateKey) {
        UcMember member = memberService.getById(id);
//        otpauth://totp/Taurus(h15521447741@163.com)?secret=TLTZI6ZESZJXAYV6
        return "otpauth://totp/Taurus("+member.getEmail()+ ")?secret="+privateKey;
    }
}
