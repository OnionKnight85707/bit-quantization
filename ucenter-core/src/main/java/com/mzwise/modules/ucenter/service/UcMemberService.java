package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.dto.MemberUpdateParam;
import com.mzwise.constant.QuantificationLevelEnum;
import com.mzwise.constant.RiskTypeEnum;
import com.mzwise.modules.ucenter.dto.*;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.vo.AdminMemberVO;
import com.mzwise.modules.ucenter.vo.CheckServiceChargeVO;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-07
 */
public interface UcMemberService extends IService<UcMember> {
    /**
     * 注册功能
     */
    UcMember register(UcMemberRegisterParam param);

    /**
     * 注册功能
     */
    void resetPassword(UcMemberResetPasswordParam param);

    /**
     * 通过邀请码查询用户
     *
     * @param promotionCode
     * @return
     */
    UcMember queryByPromotionCode(String promotionCode);


    /**
     * 根据用户名获取用户
     */
    UcMember getMemberByUsername(String username);

    /**
     * 根据Id获取用户(有缓存)
     */
    UcMember getMemberById(Long id);

    UcMember detail(Long memberId);

    /**
     * 修改头像
     *
     * @param currentUserId
     * @param url
     */
    void updateAvatar(Long currentUserId, String url);

    /**
     * 激活状态
     *
     * @param memberId
     */
    void active(Long memberId);

    /**
     * 修改昵称
     *
     * @param currentUserId
     * @param nickname
     */
    void updateNickname(Long currentUserId, String nickname);

    /**
     * 修改风险类型
     *
     * @param currentUserId
     * @param riskType
     */
    void updateRiskType(Long currentUserId, RiskTypeEnum riskType);

    /**
     * 修改密码
     *
     * @param memberId
     * @param ucUpdatePasswordParam
     * @return
     */
    void updatePassword(Long memberId, UcUpdatePasswordParam ucUpdatePasswordParam);

    /**
     * 更新并清楚缓存
     *
     * @param member
     */
    void updateAndDelCache(UcMember member);

    /**
     * 设置交易密码
     *
     * @param memberId
     * @param payPassword
     */
    void setPayPassword(Long memberId, String payPassword);

    /**
     * 设置交易用户语言
     *
     * @param memberId
     * @param lang
     */
    void setLang(Long memberId, String lang);

    /**
     * 绑定手机
     *
     * @param currentUserId
     * @param phone
     */
    void updatePhone(Long currentUserId, String areaCode, String phone);

    /**
     * 绑定邮箱
     *
     * @param currentUserId
     * @param email
     */
    void updateEmail(Long currentUserId, String email);

    /**
     * 普通修改字段
     *
     * @param memberId
     * @param param
     */
    void update(Long memberId, MemberUpdateParam param);

    /**
     * 开启手机验证
     *
     * @param currentUserId
     * @param phoneVerify
     */
    void updatePhoneVerify(Long currentUserId, Boolean phoneVerify);

    /**
     * 开启邮箱验证
     *
     * @param currentUserId
     * @param emailVerify
     */
    void updateEmailVerify(Long currentUserId, Boolean emailVerify);

    /**
     * 修改用户后清理缓存
     */
    void refreshUser(UcMember member);

    /**
     * 通过pid查询下级所有用户
     *
     * @param pid
     * @return
     */
    List<UcMember> getByPid(Long pid);

    /**
     * 通过量化等级查询会员列表
     *
     * @param levelEnum
     * @return
     */
    List<UcMember> queryByQuantificationLevel(QuantificationLevelEnum levelEnum);

    /**
     * 查询所有有效用户
     *
     * @return
     */
    List<UcMember> queryAllEffectiveMember();

    /**
     * 查询我的所有下级（不包括自己）的idList
     *
     * @param memberId
     * @return
     */
    List<Long> queryAllMySubordinateIds(Long memberId);

    /**
     * 处理合伙人返佣
     * @param serviceFeeIsSuccess 服务费是否收取成功：true：成功， false：失败
     * @param currentMemberId 当前用户id
     * @param orderId 订单id
     * @param deductionAward 扣除奖励金额数目
     * @param calcProfit 实际参与计算佣金的收益
     * @param originProfit 原来真实收益
     */
    void handlePartnerCommission(boolean serviceFeeIsSuccess, Long currentMemberId, String orderId, BigDecimal deductionAward, BigDecimal calcProfit, BigDecimal originProfit);

    /**
     * 新增欠款
     * @param memberId 会员id
     * @param arrears 欠款
     */
    void addArrears(Long memberId, BigDecimal arrears);

    /**
     * 减掉欠款
     * @param memberId 会员id
     * @param arrears 欠款
     */
    void reduceArrears(Long memberId, BigDecimal arrears);

    /**
     *  检查用户服务费
     * @return
     */
    List<CheckServiceChargeVO> checkServiceCharge();

    /**
     * 欠费次数置零
     * @param memberId
     */
    void reminderTimesToZero(Long memberId);

    /**
     * 更新用户登录信息
     * @param request
     * @param memberId
     */
    void updateMemberLoginInfo(HttpServletRequest request, Long memberId);

    /**
     *  获取下级最大的返佣额度
     * @param parentId
     * @return
     */
    BigDecimal subMaxCommissionRate(Long parentId);

    String getParentNickname(Long parentId);

    Integer getMySubCount(Long parentId);

    List<UcMember> queryAllPartner();

    void logout(Long id);
}
