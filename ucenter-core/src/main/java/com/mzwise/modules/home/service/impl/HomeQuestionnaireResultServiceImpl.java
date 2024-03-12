package com.mzwise.modules.home.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.constant.RiskTypeEnum;
import com.mzwise.modules.home.dto.AdminQuestionnaireResultParam;
import com.mzwise.modules.home.entity.HomeQuestionnaire;
import com.mzwise.modules.home.entity.HomeQuestionnaireResult;
import com.mzwise.modules.home.mapper.HomeQuestionnaireResultMapper;
import com.mzwise.modules.home.service.HomeQuestionnaireResultService;
import com.mzwise.modules.home.service.HomeQuestionnaireService;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.vo.AdminQuestionnaireResultsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Service
public class HomeQuestionnaireResultServiceImpl extends ServiceImpl<HomeQuestionnaireResultMapper, HomeQuestionnaireResult> implements HomeQuestionnaireResultService {

    @Autowired
    private HomeQuestionnaireService questionnaireService;

    @Autowired
    private UcMemberService memberService;
    @Autowired
    private HomeQuestionnaireResultMapper questionnaireResultMapper;

    @Override
    public RiskTypeEnum create(Long memberId, List<AdminQuestionnaireResultParam> questionnaireResultParamList) {
//        Integer totalWeight = 0;
        Integer totalScore = 0;
        for (AdminQuestionnaireResultParam item : questionnaireResultParamList) {
            // 记录填写内容
            HomeQuestionnaireResult questionnaireResult = new HomeQuestionnaireResult();
            BeanUtils.copyProperties(item, questionnaireResult);
            questionnaireResult.setMemberId(memberId);
            save(questionnaireResult);
            // 测试结果->风险类型
            HomeQuestionnaire question = questionnaireService.getById(item.getQuestionnaireId());
            String[] scores = question.getScores().split("\\|");
            totalScore += Integer.valueOf(scores[item.getAnswerIndex()]);
//            totalWeight += question.getWeight();
        }
//        BigDecimal score = BigDecimal.valueOf(totalScore);
        RiskTypeEnum riskType;
        if (totalScore > 45) {
            riskType = RiskTypeEnum.RADICAL;
        } else if (totalScore > 35) {
            riskType = RiskTypeEnum.AGGRESSIVE;
        } else if (totalScore > 25) {
            riskType = RiskTypeEnum.BALANCED;
        } else if (totalScore > 20) {
            riskType = RiskTypeEnum.STEADY;
        } else {
            riskType = RiskTypeEnum.CAUTIOUS;
        }
        memberService.updateRiskType(memberId, riskType);
        return riskType;
    }

    @Override
    public Page<AdminQuestionnaireResultsVO> getOneByMemberId(Long memberId, Integer pageNum, Integer pageSize) {
        Page<AdminQuestionnaireResultsVO> page = new Page<>(pageNum, pageSize);
        Page<AdminQuestionnaireResultsVO> resultsVOPage = questionnaireResultMapper.queryByMemberId(page, memberId);
        return resultsVOPage;
    }
}
