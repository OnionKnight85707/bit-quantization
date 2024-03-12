package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.util.LocaleSourceUtil;
import com.mzwise.modules.ucenter.entity.UcQuestionType;
import com.mzwise.modules.ucenter.mapper.UcQuestionTypeMapper;
import com.mzwise.modules.ucenter.service.UcQuestionTypeService;
import com.mzwise.modules.ucenter.vo.UcQuestionTypeParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UcQuestionTypeServiceImpl extends ServiceImpl<UcQuestionTypeMapper, UcQuestionType> implements UcQuestionTypeService {

    @Autowired
    private UcQuestionTypeMapper ucQuestionTypeMapper;

    @Override
    public void saveOrUpdate(UcQuestionTypeParam questionTypeParam) {
        UcQuestionType questionType = new UcQuestionType();
        BeanUtils.copyProperties(questionTypeParam, questionType);
        saveOrUpdate(questionType);
    }

    @Override
    public List<UcQuestionType> listAllByLocale() {
        QueryWrapper<UcQuestionType> wrapper = new QueryWrapper<>();
        String language = LocaleSourceUtil.getLanguage();
        wrapper.lambda().eq(UcQuestionType::getLanguage, language)
                .orderByAsc(UcQuestionType::getSort);
        return list(wrapper);
    }

    @Override
    public List<UcQuestionType> listAll() {
        QueryWrapper<UcQuestionType> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByAsc(UcQuestionType::getSort);
        return list(wrapper);
    }

}
