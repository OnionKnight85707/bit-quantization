package com.mzwise.modules.ucenter.mapper;

import com.mzwise.modules.ucenter.entity.UcCommonQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface UcCommonQuestionMapper extends BaseMapper<UcCommonQuestion> {

    /**
     * 常见问题返回每个类别的前#{limit}个
     *
     * @param limit
     * @return
     */
    List<UcCommonQuestion> showPageView(@Param("limit") Integer limit);
}
