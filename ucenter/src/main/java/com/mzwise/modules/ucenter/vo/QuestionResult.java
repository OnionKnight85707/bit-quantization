package com.mzwise.modules.ucenter.vo;

import com.mzwise.modules.ucenter.entity.UcCommonQuestion;
import lombok.Data;

import java.util.List;

@Data
public class QuestionResult {

    private QuestionIdType idType;

    private List<UcCommonQuestion> list;
}
