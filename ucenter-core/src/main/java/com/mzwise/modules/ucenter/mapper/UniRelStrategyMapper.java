package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.common.dto.IdAndNameVo;
import com.mzwise.modules.ucenter.entity.UniRelStrategy;
import com.mzwise.modules.ucenter.vo.UniRelStrategyVO;
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
public interface UniRelStrategyMapper extends BaseMapper<UniRelStrategy> {

    /**
     *  用户等级关联小策略列表
     * @return
     */
    List<UniRelStrategyVO> queryUniRelStrategy();

    /**
     * 根据用户类别id查询小策略
     * @param userTypeId 用户类别id
     * @return
     */
    List<IdAndNameVo> selSmallStrategyByUserTypeId(@Param("userTypeId") Integer userTypeId);


    void delById(@Param("userTypeId") Long userTypeId,@Param("smallStrategyId") Long smallStrategyId);

    void delRelBySmallStrategyId(@Param("smallStrategyId") Long smallStrategyId);
}
