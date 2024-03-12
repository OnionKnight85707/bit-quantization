package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.entity.UcCommonQuestion;
import com.mzwise.modules.ucenter.entity.UcQuestionType;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface UcQuestionTypeMapper extends BaseMapper<UcQuestionType> {



    List<UcQuestionType> list();
}
