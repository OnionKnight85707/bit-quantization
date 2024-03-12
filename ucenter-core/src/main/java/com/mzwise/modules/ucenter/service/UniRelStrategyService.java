package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.dto.SaveRelStrategyParam;
import com.mzwise.modules.ucenter.entity.RelStrategyParam;
import com.mzwise.modules.ucenter.entity.UniRelStrategy;
import com.mzwise.modules.ucenter.vo.UniRelStrategyVO;
import com.mzwise.modules.ucenter.vo.UserTypeStrategyVo;

import java.util.List;


public interface UniRelStrategyService extends IService<UniRelStrategy> {


    /**
     *  用户等级关联小策略列表
     * @return
     */
    List<UniRelStrategyVO> queryUniRelStrategy();


    /**
     *  将小策略关联至对应等级用户
     * @return
     */
    UniRelStrategy associationSmallStrategy(RelStrategyParam param);

    /**
     * 用户类型和策略列表
     * @param userTypeId
     * @return
     */
    List<UserTypeStrategyVo> userTypeStrategyList(Integer userTypeId);

    /**
     * 保存用户类型和策略关系
     * @param param
     */
    void saveRelStrategy(SaveRelStrategyParam param);

    /**
     *  删除用户类型下的策略关系
     * @param userTypeId
     * @param smallStrategyId
     */
    void delRelStrategy(Long userTypeId,Long smallStrategyId);

    /**
     *  删除小策略之后删除其关联表中的数据
     * @param smallStrategyId
     */
    void delRelBySmallStrategyId(Long smallStrategyId);
}
