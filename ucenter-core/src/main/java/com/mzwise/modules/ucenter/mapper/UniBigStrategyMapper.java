package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.dto.BigStrategyParam;
import com.mzwise.modules.ucenter.entity.UniBigStrategy;
import com.mzwise.modules.ucenter.vo.BigStrategyVO;
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
public interface UniBigStrategyMapper extends BaseMapper<UniBigStrategy> {

    /**
     *  大策略列表
     * @return
     */
    List<BigStrategyVO> queryUniBigStrategy();

    /**
     *  根据名称查询一条记录
     * @param name
     * @return
     */
    UniBigStrategy getByName(@Param("name") String name);

    void delById(@Param("id") Long id);
}
