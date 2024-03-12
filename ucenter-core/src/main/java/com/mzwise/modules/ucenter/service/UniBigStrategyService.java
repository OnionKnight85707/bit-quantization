package com.mzwise.modules.ucenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.dto.BigStrategyParam;
import com.mzwise.modules.ucenter.entity.UniBigStrategy;
import com.mzwise.modules.ucenter.vo.BigStrategyVO;

import java.util.List;

public interface UniBigStrategyService extends IService<UniBigStrategy> {



    /**
     *  查询大类策略列表
     * @return
     */
    List<BigStrategyVO> queryUniBigStrategy();

    /**
     *  新增大类策略
     * @param param
     * @return
     */
    UniBigStrategy addUniBigStrategy(BigStrategyParam param);

    /**
     *  修改大类策略
     * @param strategy
     */
    void updateUniBigStrategy(UniBigStrategy strategy);

    /**
     *  根据名称查询一条记录
     * @param name
     * @return
     */
    UniBigStrategy getByName(String name);

    /**
     *  单独返回大策略列表
     * @return
     */
    List<UniBigStrategy> singlelist();

    void delBigStrategy(Long id);
}
