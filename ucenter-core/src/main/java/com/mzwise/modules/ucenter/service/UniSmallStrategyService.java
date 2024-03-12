package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.dto.UniSmallStrategyParam;
import com.mzwise.modules.ucenter.entity.UniBigStrategy;
import com.mzwise.modules.ucenter.entity.UniSmallStrategy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UniSmallStrategyService extends IService<UniSmallStrategy> {

    /**
     *  查询小类策略列表
     * @return
     */
    List<UniSmallStrategy> queryUniSmallStrategy();

    /**
     *  新增小类策略
     * @param param
     * @return
     */
    UniSmallStrategy addUniSmallStrategy(UniSmallStrategyParam param);

    /**
     *  修改小类策略
     * @param uniSmallStrategy
     */
    void updateUniSmallStrategy(UniSmallStrategy uniSmallStrategy);

    UniSmallStrategy getByName(String name);


    /**
     * 检查IP是否符合 访问规则
     * @param token
     * @param ip
     * @return
     */
    boolean checkIp(String token ,String ip);


    /**
     *  模版id判断模版是否被使用
     * @param templateId
     * @return
     */
    Integer countByTemplateId(Long templateId);


    Integer countBySmallStrategyId(Long smallStrategyId);

    /**
     *  根据id删除小策略
     * @param id
     */
    void delSmallStrategy(Long id);

    Integer countByBigStrategyId(Long bigStrategyId);

    Integer getOutSignalType(String token);
}
