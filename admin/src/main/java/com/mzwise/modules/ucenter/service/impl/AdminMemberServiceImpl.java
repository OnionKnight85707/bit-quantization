package com.mzwise.modules.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.exception.ApiException;
import com.mzwise.constant.AdminLogActionEnum;
import com.mzwise.constant.AdminLogModuleEnum;
import com.mzwise.constant.MemberStatusEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.service.UmsAdminLogService;
import com.mzwise.modules.ucenter.dto.UcPartnerParam;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.modules.ucenter.mapper.UcMemberMapper;
import com.mzwise.modules.ucenter.service.AdminMemberService;
import com.mzwise.modules.ucenter.service.DictionaryService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import com.mzwise.modules.ucenter.vo.AdminMemberVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author piao
 * @Date 2021/05/28
 */
@Service
public class AdminMemberServiceImpl extends ServiceImpl<UcMemberMapper, UcMember> implements AdminMemberService {

    // 合伙人最小佣金比例
    private final static BigDecimal minPartnerCommissionRate = new BigDecimal("0.00001");

    @Autowired
    private UcMemberMapper memberMapper;
    @Autowired
    private UcWalletService walletService;
    @Autowired
    private UcMemberService ucMemberService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private UmsAdminLogService adminLogService;

    @Override
    public Page<AdminMemberVO> listAllMembersSelective(String nickname, String phone, String email, Long parentId, Boolean phoneVerify, Boolean emailVerify, Boolean isEffective, Boolean miningIsEff, String orderColumn, String orderDirection, Date beginDate, Date endDate, MemberStatusEnum status, Integer pageNum, Integer pageSize) {
        Page<AdminMemberVO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcMember> wrapper = new QueryWrapper<>();
        wrapper.ge("um.id", 1L);
        if (StringUtils.isNotBlank(nickname)) {
            wrapper.lambda().likeRight(UcMember::getNickname, nickname);
        }
        if (StringUtils.isNotBlank(phone)) {
            wrapper.lambda().likeRight(UcMember::getPhone, phone);
        }
        if (StringUtils.isNotBlank(email)) {
            wrapper.lambda().likeRight(UcMember::getEmail, email);
        }
        if (parentId != null) {
            wrapper.lambda().eq(UcMember::getParentId, parentId);
        }
        if (phoneVerify != null) {
            wrapper.lambda().eq(UcMember::getPhoneVerify, phoneVerify);
        }
        if (emailVerify != null) {
            wrapper.lambda().eq(UcMember::getEmailVerify, emailVerify);
        }
        if (isEffective != null) {
            wrapper.lambda().eq(UcMember::getIsEffective, isEffective);
        }
        if (miningIsEff != null) {
            wrapper.lambda().eq(UcMember::getMiningIsEff, miningIsEff);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(UcMember::getRegistrationTime, beginDate, endDate);
        }
        if (status != null && status != null) {
            wrapper.lambda().eq(UcMember::getStatus, status);
        }
        Page<AdminMemberVO> memberVOPage = memberMapper.listAllMemberSelective(page, orderColumn, orderDirection, wrapper);
        List<AdminMemberVO> memberVOList = memberVOPage.getRecords();
        for (AdminMemberVO adminMemberVO : memberVOList) {
            HashMap<String, UcWallet> walletsMap = walletService.getWalletsMap(adminMemberVO.getId());
            // 量化钱包
            BigDecimal quantServiceBalance = BigDecimal.ZERO;
            UcWallet quantWallet = walletsMap.get(WalletTypeEnum.QUANT.getName());
            UcWallet quantCommunityWallet = walletsMap.get(WalletTypeEnum.QUANT_COMMUNITY.getName());
            UcWallet quantServiceWallet = walletsMap.get(WalletTypeEnum.QUANT_SERVICE.getName());
            if (quantServiceWallet != null) {
                quantServiceBalance = quantServiceWallet.getBalance();
            }
            if ( ! ObjectUtils.isEmpty(quantWallet)) {
                BigDecimal quantBalance = quantWallet.getBalance().add(quantCommunityWallet.getBalance()).add(quantServiceBalance);
                adminMemberVO.setQuantifyAccount(quantBalance);
            }
            // 平台币钱包
            UcWallet platformWallet = walletsMap.get(WalletTypeEnum.PLATFORM.getName());
            UcWallet platformShareWallet = walletsMap.get(WalletTypeEnum.PLATFORM_SHARE.getName());
            if ( ! ObjectUtils.isEmpty(platformWallet) && ! ObjectUtils.isEmpty(platformShareWallet)) {
                BigDecimal platformBalance = platformWallet.getBalance().add(platformShareWallet.getBalance());
                adminMemberVO.setBteAccount(platformBalance);
            }
            adminMemberVO.setServiceFeeAccount(quantServiceBalance);
            adminMemberVO.setParentNickname(ucMemberService.getParentNickname(adminMemberVO.getParentId()));
            adminMemberVO.setFirstLevelNum(ucMemberService.getMySubCount(adminMemberVO.getId()));
        }
        return memberVOPage;
    }

    /**
     * 查看上级返佣额度
     *
     * @param param
     * @return
     */
    @Override
    public void checkParentCommissionRate(UcPartnerParam param) {
        DecimalFormat df=new DecimalFormat("0.00%");
        if (param.getPartnerCommissionRate().compareTo(dictionaryService.getPartnerMaxCommissionRatio()) == 1) {
            throw new ApiException("超出最大合伙人返佣比例:" + df.format(dictionaryService.getPartnerMaxCommissionRatio()));
        }
        UcMember currentMember = memberMapper.selectById(param.getId());
        BigDecimal currentRate = param.getPartnerCommissionRate();
        if (minPartnerCommissionRate.compareTo(currentRate) == 1) {
            throw new ApiException("不能超出最小佣金设置范围");
        }
        if (currentMember.getParentId() != null && currentMember.getParentId() != 0) {
            UcMember parent = memberMapper.selectById(currentMember.getParentId());
            if (currentRate.compareTo(parent.getPartnerCommissionRate()) == 1) {
                throw new ApiException("上级的返佣额度为:" + df.format(parent.getPartnerCommissionRate()) + "，返佣额度不能大于上级");
            }
        }
        // 设置下级比例时，此处判断额度不能小于任何一个下级
        BigDecimal subMaxRate = baseMapper.checkSubCommissionRate(currentMember.getId());
        if(subMaxRate!=null&&currentRate.compareTo(subMaxRate)<0){
            throw new ApiException("下级的最大返佣额度为:"+df.format(subMaxRate)+",返佣额度不能小于该下级");
        }
    }

    /**
     * 修改合伙人
     *
     * @param beforeUpdateMember 修改之前的用户信息
     * @param ucPartnerParam     需要修改的合伙人参数
     * @return
     */
    @Override
    public CommonResult updatePartner(UcMember beforeUpdateMember, UcPartnerParam ucPartnerParam) {
        UcMember member = new UcMember();
        // 检查参数
        if (ucPartnerParam.getIsPartner()){
            this.checkParentCommissionRate(ucPartnerParam);
        }
        BeanUtils.copyProperties(ucPartnerParam, member);
        this.updateById(member);
        // 记录日志
        UcPartnerParam beforeParam = new UcPartnerParam();
        BeanUtils.copyProperties(beforeUpdateMember, beforeParam);
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.UPDATE_PARTNER.getValue(),
                JSON.toJSONString(beforeParam), JSON.toJSONString(ucPartnerParam), "修改合伙人"));
        return CommonResult.success();
    }

}
