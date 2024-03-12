package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UniTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 666
 * @since 2022-08-02
 */
public interface UniTemplateMapper extends BaseMapper<UniTemplate> {

    Page<UniTemplate> list(@Param("page") Page<UniTemplate> page);

    UniTemplate getOneById(Integer id);

    Integer insertSelective(UniTemplate template);

    void updateByPrimaryKeySelective(UniTemplate template);

    void deleteByTemplateId(@Param("id") Long id);
}
