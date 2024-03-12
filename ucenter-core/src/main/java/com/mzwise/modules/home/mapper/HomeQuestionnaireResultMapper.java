package com.mzwise.modules.home.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.home.entity.HomeQuestionnaireResult;
import com.mzwise.modules.ucenter.vo.AdminQuestionnaireResultsVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface HomeQuestionnaireResultMapper extends BaseMapper<HomeQuestionnaireResult> {
    /**
     * 查看某用户回答的风险测评
     *
     * @param page
     * @param memberId
     * @return
     */
    Page<AdminQuestionnaireResultsVO> queryByMemberId(Page<AdminQuestionnaireResultsVO> page, @Param("memberId") Long memberId);

}
