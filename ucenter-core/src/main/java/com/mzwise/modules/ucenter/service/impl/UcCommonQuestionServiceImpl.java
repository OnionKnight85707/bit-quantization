package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.util.LocaleSourceUtil;
import com.mzwise.constant.CommonQuestionTypeEnum;
import com.mzwise.modules.ucenter.dto.UcCommonQuestionParam;
import com.mzwise.modules.ucenter.entity.UcCommonQuestion;
import com.mzwise.modules.ucenter.mapper.UcCommonQuestionMapper;
import com.mzwise.modules.ucenter.service.UcCommonQuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
public class UcCommonQuestionServiceImpl extends ServiceImpl<UcCommonQuestionMapper, UcCommonQuestion> implements UcCommonQuestionService {
    @Autowired
    private UcCommonQuestionMapper commonQuestionMapper;

    @Override
    public void create(UcCommonQuestionParam commonQuestionParam) {
        UcCommonQuestion commonQuestion = new UcCommonQuestion();
        BeanUtils.copyProperties(commonQuestionParam, commonQuestion);
        save(commonQuestion);
    }

    @Override
    public List<UcCommonQuestion> listAll() {
        QueryWrapper<UcCommonQuestion> wrapper = new QueryWrapper<>();
        String language = LocaleSourceUtil.getLanguage();
        wrapper.lambda().eq(UcCommonQuestion::getLanguage, language)
                .eq(UcCommonQuestion::getIsShow, true)
                .orderByDesc(UcCommonQuestion::getIsTop);
        return list(wrapper);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<UcCommonQuestion> listPage(String language, String title, Integer type, Integer pageNum, Integer pageSize) {
        Page<UcCommonQuestion> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcCommonQuestion> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UcCommonQuestion> lambda = wrapper.lambda();
        if (StringUtils.isNotBlank(language)) {
            lambda.eq(UcCommonQuestion::getLanguage, language);
        }
        if (StringUtils.isNotBlank(title)) {
            lambda.like(UcCommonQuestion::getTitle, title);
        }
        if (type != null) {
            lambda.eq(UcCommonQuestion::getType, type);
        }
        lambda.orderByDesc(UcCommonQuestion::getIsTop, UcCommonQuestion::getLanguage, UcCommonQuestion::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        QueryWrapper<UcCommonQuestion> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(UcCommonQuestion::getId, Arrays.asList(ids));
        remove(wrapper);
    }

    @Override
    public void updateIsShow(Long id) {
        UcCommonQuestion commonQuestion = getById(id);
        commonQuestion.setIsShow(!commonQuestion.getIsShow());
        updateById(commonQuestion);
    }

    @Override
    public void updateIsTop(Long id) {
        UcCommonQuestion commonQuestion = getById(id);
        commonQuestion.setIsTop(!commonQuestion.getIsTop());
        updateById(commonQuestion);
    }

    @Override
    public List<UcCommonQuestion> showPageView(Integer limit) {
        return commonQuestionMapper.showPageView(limit);
    }

    /**
     * 根据常见问题type 查询
     *
     * @param type
     * @return
     */
    @Override
    public List<UcCommonQuestion> listByType(CommonQuestionTypeEnum type) {
        QueryWrapper<UcCommonQuestion> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcCommonQuestion::getType, type);
        return list(wrapper);
    }

    @Override
    public List<UcCommonQuestion> listByType(Integer type) {
        QueryWrapper<UcCommonQuestion> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcCommonQuestion::getType, type);
        return list(wrapper);
    }
}
