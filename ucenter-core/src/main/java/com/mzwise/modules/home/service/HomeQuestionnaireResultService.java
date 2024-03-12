package com.mzwise.modules.home.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.constant.RiskTypeEnum;
import com.mzwise.modules.home.dto.AdminQuestionnaireResultParam;
import com.mzwise.modules.ucenter.vo.AdminQuestionnaireResultsVO;
import com.mzwise.modules.home.entity.HomeQuestionnaireResult;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface HomeQuestionnaireResultService extends IService<HomeQuestionnaireResult> {
    /**
     * 回答风险测评
     *
     * @param memberId
     * @param questionnaireResultParamList
     */
    RiskTypeEnum create(Long memberId, List<AdminQuestionnaireResultParam> questionnaireResultParamList);

    /**
     * 查看某用户回答的风险测评
     *
     * @param memberId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminQuestionnaireResultsVO> getOneByMemberId(Long memberId, Integer pageNum, Integer pageSize);

}
