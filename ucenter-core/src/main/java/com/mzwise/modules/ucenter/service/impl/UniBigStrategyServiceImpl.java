package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.exception.ApiException;
import com.mzwise.modules.ucenter.dto.BigStrategyParam;
import com.mzwise.modules.ucenter.entity.UniBigStrategy;
import com.mzwise.modules.ucenter.mapper.UniBigStrategyMapper;
import com.mzwise.modules.ucenter.service.UniBigStrategyService;
import com.mzwise.modules.ucenter.service.UniSmallStrategyService;
import com.mzwise.modules.ucenter.vo.BigStrategyVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniBigStrategyServiceImpl extends ServiceImpl<UniBigStrategyMapper, UniBigStrategy> implements UniBigStrategyService {


    @Autowired
    private UniSmallStrategyService smallStrategyService;

    /**
     *  查询大类策略列表
     * @return
     */
    @Override
    public List<BigStrategyVO> queryUniBigStrategy() {
        return baseMapper.queryUniBigStrategy();
    }

    /**
     *  新增大类策略
     * @param param
     * @return
     */
    @Override
    public UniBigStrategy addUniBigStrategy(BigStrategyParam param) {
        UniBigStrategy bigStrategy = new UniBigStrategy();
        bigStrategy.setName(param.getName());
        bigStrategy.setSort(param.getSort());
        //baseMapper.addUniBigStrategy(bigStrategy.getName(),bigStrategy.getSort());
        save(bigStrategy);
        return bigStrategy;
    }

    /**
     *  修改大类策略
     * @param strategy
     */
    @Override
    public void updateUniBigStrategy(UniBigStrategy strategy) {
        UpdateWrapper<UniBigStrategy> wrapper =new UpdateWrapper<>();

        wrapper.lambda().eq(UniBigStrategy::getId,strategy.getId()).set(UniBigStrategy::getName,strategy.getName()).set(UniBigStrategy::getSort,strategy.getSort());
        update(wrapper);
    }

    @Override
    public UniBigStrategy getByName(String name) {
        QueryWrapper<UniBigStrategy> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(UniBigStrategy::getName,name);
        return getOne(wrapper);
    }

    @Override
    public List<UniBigStrategy> singlelist() {
        return list();
    }

    @Override
    public void delBigStrategy(Long id) {
        if (smallStrategyService.countByBigStrategyId(id)>0){
            throw new ApiException("该大策略包含有小策略，无法删除(若要删除，请先删除该大策略下的所有小策略!)");
        }
        baseMapper.delById(id);
    }
}
