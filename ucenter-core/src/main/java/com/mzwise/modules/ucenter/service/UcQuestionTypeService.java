package com.mzwise.modules.ucenter.service;

import com.mzwise.modules.ucenter.entity.UcQuestionType;
import com.mzwise.modules.ucenter.vo.UcQuestionTypeParam;

import java.util.List;

public interface UcQuestionTypeService {
    void saveOrUpdate(UcQuestionTypeParam questionTypeParam);

    List<UcQuestionType> listAllByLocale();

    List<UcQuestionType> listAll();
}
