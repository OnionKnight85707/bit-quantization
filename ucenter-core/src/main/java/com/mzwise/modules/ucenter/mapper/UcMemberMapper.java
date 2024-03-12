package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.vo.AdminMemberHomePageVO;
import com.mzwise.modules.ucenter.vo.AdminMemberVO;
import com.mzwise.modules.ucenter.vo.CheckServiceChargeVO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-07
 */
public interface UcMemberMapper extends BaseMapper<UcMember> {

    /**
     * 查询我的所有下级（不包括自己）
     *
     * @param memberId
     * @return
     */
    List<Long> queryAllMySubordinates(Long memberId);

    /**
     * 分页条件查询所有用户
     *
     * @param page
     * @param orderColumn
     * @param orderDirection
     * @param wrapper
     * @return
     */
    Page<AdminMemberVO> listAllMemberSelective(Page<AdminMemberVO> page, @Param("orderColumn") String orderColumn, @Param("orderDirection") String orderDirection, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 展示会员详情-主页
     *
     * @param memberId
     * @return
     */
    AdminMemberHomePageVO showMemberHomePage(@Param("memberId") Long memberId);

    /**
     * 新增欠款
     *
     * @param memberId 会员id
     * @param arrears  欠款
     */
    void addArrears(@Param("memberId") Long memberId, @Param("arrears") BigDecimal arrears);

    /**
     * 减掉欠款
     *
     * @param memberId 会员id
     * @param arrears  欠款
     */
    void reduceArrears(@Param("memberId") Long memberId, @Param("arrears") BigDecimal arrears);

    /**
     * 增加合伙人佣金
     * @param memberId 用户id
     * @param commission 佣金
     */
    void addPartnerCommission(@Param("memberId") Long memberId, @Param("commission") BigDecimal commission);

    /**
     * 新增量化账户
     * @param memberId
     */
    void addUcQuant(@Param("memberId") Long memberId);

    /**
     *  检查用户服务费
     * @return
     */
    List<CheckServiceChargeVO> checkServiceCharge();

    /**
     * 欠费次数置零
     * @param memberId
     */
    void reminderTimesToZero(@Param("memberId") Long memberId);


    /**
     *  查询下级最大的返佣额度
     * @param parentId
     * @return
     */
    BigDecimal checkSubCommissionRate(@Param("parentId") Long parentId);

    String getParentNickname(@Param("parentId") Long parentId);

    Integer getMySubCount(@Param("parentId") Long parentId);
}
