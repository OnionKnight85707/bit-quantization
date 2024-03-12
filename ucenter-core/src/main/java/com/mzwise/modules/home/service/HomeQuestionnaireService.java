package com.mzwise.modules.home.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.home.dto.HomeQuestionnaireParam;
import com.mzwise.modules.home.entity.HomeQuestionnaire;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface HomeQuestionnaireService extends IService<HomeQuestionnaire> {
    /**
     * 新增风险测评
     *
     * @param questionnaireParam
     */
    void create(HomeQuestionnaireParam questionnaireParam);

    /**
     * 逻辑删除风险测评
     *
     * @param id
     */
    void logicDelete(Long id);

    /**
     * 展示所有风险测评
     * @return
     */
    List<HomeQuestionnaire> listAll();

    /**
     * 展示所有风险测评
     * @return
     */
    Page<HomeQuestionnaire> listAll(Integer pageNum, Integer pageSize);

}
