package com.mzwise.modules.home.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.util.LocaleSourceUtil;
import com.mzwise.modules.home.dto.HomeQuestionnaireParam;
import com.mzwise.modules.home.entity.HomeQuestionnaire;
import com.mzwise.modules.home.mapper.HomeQuestionnaireMapper;
import com.mzwise.modules.home.service.HomeQuestionnaireService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Service
public class HomeQuestionnaireServiceImpl extends ServiceImpl<HomeQuestionnaireMapper, HomeQuestionnaire> implements HomeQuestionnaireService {
    @Override
    public void create(HomeQuestionnaireParam questionnaireParam) {
        HomeQuestionnaire questionnaire = new HomeQuestionnaire();
        BeanUtils.copyProperties(questionnaireParam, questionnaire);
        save(questionnaire);
    }

    @Override
    public void logicDelete(Long id) {
        UpdateWrapper<HomeQuestionnaire> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(HomeQuestionnaire::getId, id);
        wrapper.lambda().set(HomeQuestionnaire::getDeleted, true);
        update(wrapper);
    }

    @Override
    public List<HomeQuestionnaire> listAll() {
        QueryWrapper<HomeQuestionnaire> wrapper = new QueryWrapper<>();
//        wrapper.lambda().eq(HomeQuestionnaire::getDeleted, false)
//                .orderByAsc(HomeQuestionnaire::getSort);
        String language= LocaleSourceUtil.getLanguage();
        wrapper.lambda().eq(HomeQuestionnaire::getLanguage,language).eq(HomeQuestionnaire::getDeleted, false)
                .orderByAsc(HomeQuestionnaire::getSort);
        return list(wrapper);
    }

    @Override
    public Page<HomeQuestionnaire> listAll(Integer pageNum, Integer pageSize) {
        Page<HomeQuestionnaire> page = new Page<>(pageNum, pageSize);
        QueryWrapper<HomeQuestionnaire> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(HomeQuestionnaire::getDeleted, false)
                .orderByAsc(HomeQuestionnaire::getSort);
        return page(page, wrapper);
    }
}
