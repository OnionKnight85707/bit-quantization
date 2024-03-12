package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.dto.MemberUpdateParam;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.util.HttpServletUtil;
import com.mzwise.common.util.IpUtils;
import com.mzwise.constant.*;
import com.mzwise.modules.ucenter.dto.UcMemberRegisterParam;
import com.mzwise.modules.ucenter.dto.UcMemberResetPasswordParam;
import com.mzwise.modules.ucenter.dto.UcUpdatePasswordParam;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.modules.ucenter.event.RegisteEvent;
import com.mzwise.modules.ucenter.mapper.UcMemberMapper;
import com.mzwise.modules.ucenter.service.*;
import com.mzwise.modules.ucenter.vo.CheckServiceChargeVO;
import com.mzwise.modules.ucenter.vo.PartnerCommissionCalcVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-07
 */
@Service
@Slf4j
public class UcMemberServiceImpl extends ServiceImpl<UcMemberMapper, UcMember> implements UcMemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UcMemberServiceImpl.class);

    @Autowired
    private UcMemberCacheService memberCacheService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UcMemberMapper memberMapper;
    @Autowired
    private UcPartnerCommissionDetailService partnerCommissionDetailService;
    @Autowired
    private UcTransactionService transactionService;
    @Autowired
    private UcProfitDetailService profitDetailService;
    @Autowired
    private UcProfitService profitService;
    @Autowired
    private UcWalletService walletService;
    @Autowired
    private UcStatisticsService statisticsService;
    @Autowired
    private UcPartnerLevelService partnerLevelService;
    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UcMember register(UcMemberRegisterParam param) {
        UcMember member = new UcMember();
        BeanUtils.copyProperties(param, member, "promotionCode");
        if (!StringUtils.isEmpty(member.getPhone())) {
            member.setPhoneVerify(true);
        } else if (!StringUtils.isEmpty(member.getEmail())) {
            member.setEmailVerify(true);
        } else {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.HomeQuestionnaireController_001));
        }
        member.setUsername(!StringUtils.isEmpty(member.getPhone()) ? member.getPhone() : member.getEmail());
        // 使用邮箱或手机号充当初始昵称
        member.setNickname(member.getUsername());
        member.setUsername( ! StringUtils.isEmpty(member.getPhone()) ? member.getPhone() : member.getEmail());
        member.setRegistrationTime(new Date());
        member.setStatus(MemberStatusEnum.NORMAL);
        //查询是否有相同用户名的用户
        UcMember memberByUsername = getMemberByUsername(member.getUsername());
        if (memberByUsername != null) {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberServiceImpl_004));
        }

        // 因为注册完自动成为量化用户，所以is_effective默认为true
        member.setIsEffective(true);

        baseMapper.insert(member);

        // 自动新增量化账户
        baseMapper.addUcQuant(member.getId());

        // 发布注册事件
        RegisteEvent event = new RegisteEvent(applicationContext);
        event.setMemberId(member.getId());
        applicationContext.publishEvent(event);
        return member;
    }

    @Override
    public void resetPassword(UcMemberResetPasswordParam param) {
        UcMember member = getMemberByUsername(param.getAccount());
        Assert.isTrue(member != null,  localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberServiceImpl_001));
        member.setPassword(param.getPassword());
        member.setModifyTime(new Date());
        memberCacheService.putModifyUser(member);
        updateAndRemoveCache(member);
    }

    @Override
    public UcMember queryByPromotionCode(String promotionCode) {
        QueryWrapper<UcMember> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcMember::getPromotionCode, promotionCode);
        return getOne(wrapper);
    }

    @Override
    public UcMember getMemberByUsername(String username) {
        return findByEmailOrPhone(username);
    }

    @Override
    public UcMember getMemberById(Long id) {
        UcMember member = memberCacheService.get(id.toString());
        if (member != null) {
            return member;
        }
        member = getById(id);
        if (member != null) {
            memberCacheService.set(member);
        }
        return member;
    }

    @Override
    public UcMember detail(Long memberId) {
        return getById(memberId);
    }

    @Override
    public void updateAvatar(Long currentUserId, String url) {
        UcMember member = getById(currentUserId);
        member.setAvatar(url);
        updateAndRemoveCache(member);
    }

    @Override
    public void active(Long memberId) {
        UcMember member = getById(memberId);
        member.setIsEffective(true);
        member.setEffectiveTime(new Date());
        updateAndRemoveCache(member);
    }

    @Override
    public void updateNickname(Long currentUserId, String nickname) {
        UcMember member = getById(currentUserId);
        member.setNickname(nickname);
        updateAndRemoveCache(member);
    }

    @Override
    public void updateRiskType(Long currentUserId, RiskTypeEnum riskType) {
        UcMember member = getById(currentUserId);
        member.setRiskType(riskType);
        updateAndRemoveCache(member);
    }

    @Override
    public void updatePassword(Long memberId, UcUpdatePasswordParam ucUpdatePasswordParam) {
        String newPassword = ucUpdatePasswordParam.getNewPassword();
        UcMember member = getById(memberId);
        member.setPassword(newPassword);
        member.setModifyTime(new Date());
        memberCacheService.putModifyUser(member);
        updateAndRemoveCache(member);
    }

    /**
     * 修改并清除缓存
     *
     * @param member
     */
    private void updateAndRemoveCache(UcMember member) {
        updateById(member);
        refreshUser(member);
    }

    @Override
    public void updateAndDelCache(UcMember member) {
        updateById(member);
        memberCacheService.del(member.getId());
    }

    @Override
    public void setPayPassword(Long memberId, String payPassword) {
        UcMember member = getById(memberId);
        member.setPayPassword(payPassword);
        member.setIsSetPayPassword(true);
        updateAndRemoveCache(member);
    }

    @Override
    public void setLang(Long memberId, String lang) {
        UcMember member = getById(memberId);
        member.setLanguageCountry(lang);
        updateAndRemoveCache(member);
    }

    @Override
    public void updatePhone(Long currentUserId, String areaCode, String phone) {
        UcMember already = getMemberByUsername(phone);
        Assert.isTrue(already == null, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberServiceImpl_004));
        UcMember member = getById(currentUserId);
        member.setPhoneVerify(true);
        member.setAreaCode(areaCode);
        member.setPhone(phone);
        // todo 没啥用
        member.setUsername(phone);
        updateAndRemoveCache(member);
    }

    @Override
    public void updateEmail(Long currentUserId, String email) {
        UcMember already = getMemberByUsername(email);
        Assert.isTrue(already == null,  localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberServiceImpl_004));
        UcMember member = getById(currentUserId);
        if (member.getEmail() == null) {
            member.setEmailVerify(true);
        }
        member.setEmail(email);
        // todo 没啥用
        member.setUsername(email);
        updateAndRemoveCache(member);
    }

    @Override
    public void update(Long memberId, MemberUpdateParam param) {
        UcMember member = getById(memberId);
        BeanUtils.copyProperties(param, member);
        updateAndRemoveCache(member);
    }

    @Override
    public void updatePhoneVerify(Long memberId, Boolean phoneVerify) {
        UcMember member = getById(memberId);
        Assert.isTrue(member.getPhone() != null,  localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberServiceImpl_002));
        member.setPhoneVerify(phoneVerify);
        updateAndRemoveCache(member);
    }

    @Override
    public void updateEmailVerify(Long memberId, Boolean emailVerify) {
        UcMember member = getById(memberId);
        Assert.isTrue(member.getEmail() != null, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberServiceImpl_003));
        member.setEmailVerify(emailVerify);
        updateAndRemoveCache(member);
    }

    @Override
    public void refreshUser(UcMember member) {
        memberCacheService.del(member.getId());
//        todo
//        MemberDetails userDetails = new MemberDetails(member);
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    public UcMember findByEmailOrPhone(String username) {
        QueryWrapper<UcMember> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcMember::getPhone, username);
        wrapper.lambda().or().eq(UcMember::getEmail, username);
        wrapper.lambda().or().eq(UcMember::getUsername, username);
        return getOne(wrapper);
    }

    @Override
    public List<UcMember> getByPid(Long pid) {
        QueryWrapper<UcMember> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcMember::getParentId, pid);
        return list(wrapper);
    }

    @Override
    public List<UcMember> queryByQuantificationLevel(QuantificationLevelEnum levelEnum) {
        QueryWrapper<UcMember> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcMember::getQuantificationLevel, levelEnum);
        return list(wrapper);
    }

    @Override
    public List<UcMember> queryAllEffectiveMember() {
        QueryWrapper<UcMember> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcMember::getIsEffective, true);
        return list(wrapper);
    }

    @Override
    public List<Long> queryAllMySubordinateIds(Long memberId) {
        List<Long> mySubordinateIds = memberMapper.queryAllMySubordinates(memberId);
        return mySubordinateIds;
    }

    /**
     * 处理合伙人返佣
     * @param serviceFeeIsSuccess 服务费是否收取成功：true：成功， false：失败
     * @param currentMemberId 当前用户id
     * @param orderId 订单id
     * @param deductionAward 扣除奖励金额数目
     * @param calcProfit 实际参与计算佣金的收益
     * @param originProfit 原来真实收益
     */
    @Override
    @Transactional
    public void handlePartnerCommission(boolean serviceFeeIsSuccess, Long currentMemberId, String orderId, BigDecimal deductionAward, BigDecimal calcProfit, BigDecimal originProfit) {
        // 标记调用此方法的第一个人
        Long firstMemberId = currentMemberId;
        if ( ! serviceFeeIsSuccess) {
            log.info("暂时不记录合伙人收益(平台扣除订单人员余额不足)：订单={}，会员={}，收益={}", orderId, currentMemberId, calcProfit);
            return;
        }
        if (BigDecimal.ZERO.compareTo(calcProfit) >= 0) {
            log.info("无需记录合伙人收益(无正向收益)：订单={}，会员={}，收益={}", orderId, currentMemberId, calcProfit);
            return;
        }
        if (ObjectUtils.isEmpty(currentMemberId) || currentMemberId == 0) {
            log.info("处理合伙人返佣：成交者={}，id有误", currentMemberId);
            return;
        }
        // 成交者
        UcMember businessMember = this.getById(currentMemberId);
        // 上一个合伙人设置的佣金比例
        BigDecimal lastSetPartnerCommissionRate = BigDecimal.ZERO;
        while (true) {
            if (currentMemberId == 0) {
                break;
            }
//            UcMember currentMember = memberMapper.selectById(currentMemberId);
            PartnerCommissionCalcVo vo = partnerLevelService.getPartnerCommissionRate(currentMemberId, businessMember.getParentId(), lastSetPartnerCommissionRate);
            if (ObjectUtils.isEmpty(vo)) {
                log.error("处理合伙人返佣：查询不到id={}的用户", currentMemberId);
                break;
            }
            // 成交者(最末端的人)可能不是合伙人
//            if ( ! currentMember.getIsPartner()) {
//                currentMemberId = currentMember.getParentId();
//                continue;
//            }
            // 当前用户可返佣比例 = vo中的计算出来的比例(calcRate)
            BigDecimal currentPartnerCommissionRate = vo.getCalcRate();
            if (currentPartnerCommissionRate.compareTo(BigDecimal.ZERO) == 1) {
                BigDecimal commission = calcProfit.multiply(currentPartnerCommissionRate).setScale(8, BigDecimal.ROUND_DOWN);
                UcWallet masterWallet = walletService.getMasterWallet(UnitEnum.USDT.getName(), currentMemberId);
                // 累加合伙人佣金
                memberMapper.addPartnerCommission(currentMemberId, commission);
                // 平台扣费
                walletService.platformDeduction(commission);
                // 合伙人钱包余额增加佣金
                walletService.addBalance(masterWallet.getId(), commission);
                // 统计：增加合伙人佣金
                statisticsService.addPartnerCommission(commission);

                String remark = "合伙人返佣：合伙人=" + currentMemberId + ", 获取返佣=" + commission
                        + ", 实际参与计算佣金的收益=" + calcProfit + ", 原来真实收益=" + originProfit + ", 订单id=" + orderId;
                String remarkUser = null;
                if ( ! ObjectUtils.isEmpty(deductionAward) && deductionAward.compareTo(BigDecimal.ZERO) == 1) {
                    // 如果deductionAward不为空，并且比0大，说明用户扣服务费用到了奖励金额
                    remarkUser = "获取合伙人返佣=" + commission + ", 参与计算的利润=" + calcProfit.setScale(2, BigDecimal.ROUND_DOWN)
                            + "(扣除奖励金额数目=" + deductionAward.setScale(2, BigDecimal.ROUND_DOWN) + "不参与合伙人佣金计算)";
                }
                // 记录流水
                transactionService.addTransactionRecord(currentMemberId, WalletTypeEnum.QUANT_SERVICE, TransactionTypeEnum.PARTNER_COMMISSION,
                        commission, BigDecimal.ZERO, TransactionStatusEnum.SUCCESS, masterWallet.getBalance().add(masterWallet.getTicket()),
                        masterWallet.getBalance().add(masterWallet.getTicket()).add(commission), remark, firstMemberId.toString());

                // 从第二个人开始(因为第一个人平台服务费已经(补)缴过了)，就要去检查补缴欠费(这次的补缴欠款里面不需要补发合伙人佣金, 因为该方法上面逻辑从第二个人(当前人)开始已经发了合伙人佣金才来补缴欠款的)
                if ( ! currentMemberId.equals(firstMemberId)) {
                    profitDetailService.repayArrears(currentMemberId, false);
                }
            }
            if (vo.getParentId() != 0) {
                currentMemberId = vo.getParentId();
                lastSetPartnerCommissionRate = vo.getSetRate();
                // 只有当前用户是合伙人情况下，lastPartnerCommissionRate才能设置为当前人合伙人比例，不然lastPartnerCommissionRate = 上一个人合伙人比例
//                if (currentMember.getIsPartner()) {
//                    lastSetPartnerCommissionRate = currentMember.getPartnerCommissionRate();
//                }
            } else {
                break;
            }
        }
    }

    /**
     * 新增欠款
     *
     * @param memberId 会员id
     * @param arrears  欠款
     */
    @Override
    public void addArrears(Long memberId, BigDecimal arrears) {
        baseMapper.addArrears(memberId, arrears);
    }

    /**
     * 减掉欠款
     *
     * @param memberId 会员id
     * @param arrears  欠款
     */
    @Override
    public void reduceArrears(Long memberId, BigDecimal arrears) {
        baseMapper.reduceArrears(memberId, arrears);
    }

    @Override
    public List<CheckServiceChargeVO> checkServiceCharge() {
        return baseMapper.checkServiceCharge();
    }

    /**
     * 欠费次数置零
     *
     * @param memberId
     */
    @Override
    public void reminderTimesToZero(Long memberId) {
        baseMapper.reminderTimesToZero(memberId);
    }

    /**
     * 更新用户登录信息
     *
     * @param request
     * @param memberId
     */
    @Override
    public void updateMemberLoginInfo(HttpServletRequest request, Long memberId) {
        UcMember member = this.getById(memberId);
        Thread thread = new Thread("更新用户登录信息") {
            @Override
            public void run() {
                String ip = IpUtils.getIP(request);
                if (ObjectUtils.isEmpty(ip)) {
                    return;
                }
                String cityInfo = HttpServletUtil.getCityInfo(ip);
                member.setLastLoginIp(ip);
                member.setIpRegion(cityInfo);
                member.setLastLoginTime(new Date());
                updateById(member);
            }
        };
        thread.start();
    }

    public static void main(String[] args) {
        String city = HttpServletUtil.getCityInfo("67.220.91.30");
        System.out.println(city);
    }


    @Override
    public BigDecimal subMaxCommissionRate(Long parentId) {
        BigDecimal subMaxCommissionRate = baseMapper.checkSubCommissionRate(parentId);
        if (subMaxCommissionRate==null||subMaxCommissionRate.compareTo(BigDecimal.ZERO)==0){
            return BigDecimal.ZERO;
        }
        return subMaxCommissionRate;
    }

    @Override
    public String getParentNickname(Long parentId) {
        return baseMapper.getParentNickname(parentId);
    }

    @Override
    public Integer getMySubCount(Long parentId) {
        return baseMapper.getMySubCount(parentId);
    }

    @Override
    public List<UcMember> queryAllPartner() {
        QueryWrapper<UcMember> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcMember::getIsPartner, true);
       return list(wrapper);
    }

    @Override
    public void logout(Long id) {

        UcMember member =getById(id);
        member.setModifyTime(new Date());
        memberCacheService.putModifyUser(member);
        updateAndRemoveCache(member);
    }
}
