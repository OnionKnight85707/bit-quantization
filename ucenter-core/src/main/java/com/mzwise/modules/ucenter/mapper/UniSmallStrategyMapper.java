package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.entity.UniSmallStrategy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 666
 * @since 2022-08-02
 */
public interface UniSmallStrategyMapper extends BaseMapper<UniSmallStrategy> {


    UniSmallStrategy getByName(@Param("name") String name);

    UniSmallStrategy getByToken(@Param("token") String token);

    /**
     * 根据用户查询所有关联的小策略
     * @param memberId
     * @return
     */
    List<UniSmallStrategy> selAllSmallStrategyByMemberId(@Param("memberId") Long memberId);

    /**
     *  模版id判断模版是否被使用
     * @param templateId
     * @return
     */
    Integer countByTemplateId(@Param("templateId") Long templateId);

    Integer countBySmallStrategyId(@Param("smallStrategyId") Long smallStrategyId);

    void delSmallStrategy(@Param("id") Long id);

    Integer countByBigStrategyId(@Param("bigStrategyId") Long bigStrategyId);

    Integer getOutSignalType(String token);
}
