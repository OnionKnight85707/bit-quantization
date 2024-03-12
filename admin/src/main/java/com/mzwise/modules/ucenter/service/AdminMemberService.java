package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.MemberStatusEnum;
import com.mzwise.modules.ucenter.dto.UcPartnerParam;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.vo.AdminMemberVO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author piao
 * @Date 2021/05/28
 */
public interface AdminMemberService extends IService<UcMember> {
    /**
     * 后台分页展示所有会员
     * @param nickname
     * @param phone
     * @param email
     * @param parentId
     * @param phoneVerify
     * @param emailVerify
     * @param isEffective
     * @param miningIsEff
     * @param orderColumn
     * @param orderDirection
     * @param beginDate
     * @param endDate
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminMemberVO> listAllMembersSelective(String nickname, String phone, String email, Long parentId, Boolean phoneVerify, Boolean emailVerify, Boolean isEffective, Boolean miningIsEff, String orderColumn, String orderDirection, Date beginDate, Date endDate, MemberStatusEnum status, Integer pageNum, Integer pageSize);


  /* *
     * 查看上级返佣额度
     * @param param
     * @return
     */
    void checkParentCommissionRate(UcPartnerParam param);

    /**
     * 修改合伙人
     * @param beforeUpdateMember 修改之前的用户信息
     * @param ucPartnerParam 需要修改的合伙人参数
     * @return
     */
    CommonResult updatePartner(UcMember beforeUpdateMember, UcPartnerParam ucPartnerParam);

}
