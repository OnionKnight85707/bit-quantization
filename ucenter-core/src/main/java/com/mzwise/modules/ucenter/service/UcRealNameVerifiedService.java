package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.constant.RealNameStatusEnum;
import com.mzwise.modules.ucenter.dto.UcRealNameFailedParam;
import com.mzwise.modules.ucenter.dto.UcRealNameSubmitApplyParam;
import com.mzwise.modules.ucenter.entity.UcRealNameVerified;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface UcRealNameVerifiedService extends IService<UcRealNameVerified> {
    /**
     * 提交实名认证
     *
     * @param realNameSubmitApplyParam
     * @return
     */
    void submitApply(Long memberId, UcRealNameSubmitApplyParam realNameSubmitApplyParam);

    /**
     * 后台 审核实名认证 通过
     *
     * @param realNameVerifiedId
     * @return
     */
    void pass(Long realNameVerifiedId);

    /**
     * 后台 审核实名认证 驳回
     *
     * @param realNameFailedParam
     * @return
     */
    void failed(UcRealNameFailedParam realNameFailedParam);

    /**
     * 查询用户的最后一条实名审核记录
     *
     * @param memberId
     * @return
     */
    UcRealNameVerified queryLastByMemberId(Long memberId);

    /**
     * 分页条件查询 所有实名认证数据
     *
     * @param realNameStatusEnum
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<UcRealNameVerified> listAll(RealNameStatusEnum realNameStatusEnum, String realName, Integer pageNum, Integer pageSize);

}
