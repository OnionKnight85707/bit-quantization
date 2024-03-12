package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.dto.IdAndNameVo;
import com.mzwise.common.exception.ApiException;
import com.mzwise.modules.ucenter.dto.SaveRelStrategyParam;
import com.mzwise.modules.ucenter.entity.RelStrategyParam;
import com.mzwise.modules.ucenter.entity.UniRelStrategy;
import com.mzwise.modules.ucenter.entity.UniUserType;
import com.mzwise.modules.ucenter.mapper.UniRelStrategyMapper;
import com.mzwise.modules.ucenter.service.UniRelStrategyService;
import com.mzwise.modules.ucenter.service.UniSmallStrategyService;
import com.mzwise.modules.ucenter.service.UniUserTypeService;
import com.mzwise.modules.ucenter.vo.UniRelStrategyVO;
import com.mzwise.modules.ucenter.vo.UserTypeStrategyVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UniRelStrategyServiceImpl extends ServiceImpl<UniRelStrategyMapper, UniRelStrategy> implements UniRelStrategyService {

    @Autowired
    private UniUserTypeService userTypeService;

    @Autowired
    private UniSmallStrategyService smallStrategyService;

    /**
     *  用户等级关联小策略列表
     * @return
     */
    @Override
    public List<UniRelStrategyVO> queryUniRelStrategy() {
        return baseMapper.queryUniRelStrategy();
    }


    /**
     *  将小策略关联至对应等级用户
     * @return
     */
    @Override
    public UniRelStrategy associationSmallStrategy(RelStrategyParam param) {
        return null;
    }

    /**
     * 用户类型和策略列表
     *
     * @param userTypeId
     * @return
     */
    @Override
    public List<UserTypeStrategyVo> userTypeStrategyList(Integer userTypeId) {
        List<UniUserType> userTypeList = userTypeService.queryUserType(userTypeId);
        if (CollectionUtils.isEmpty(userTypeList)) {
            return null;
        }
        List<UserTypeStrategyVo> list = new ArrayList<>();
        for (UniUserType temp : userTypeList) {
            UserTypeStrategyVo vo = new UserTypeStrategyVo();
            vo.setId(temp.getId());
            vo.setName(temp.getName());
            List<IdAndNameVo> smallStrategyList = baseMapper.selSmallStrategyByUserTypeId(temp.getId());
            vo.setSmallStrategyList(smallStrategyList);
            list.add(vo);
        }
        return list;
    }

    /**
     * 保存用户类型和策略关系
     *
     * @param param
     */
    @Override
    public void saveRelStrategy(SaveRelStrategyParam param) {
        Integer userTypeId = param.getUserTypeId();
        List<Integer> smallStrategyIds = param.getSmallStrategyIds();
        baseMapper.delete(Wrappers.<UniRelStrategy>query().lambda().eq(UniRelStrategy::getUserTypeId, userTypeId));
        List<UniRelStrategy> list = new ArrayList<>();
        for (Integer smallStrategyId : smallStrategyIds) {
            UniRelStrategy e = new UniRelStrategy();
            e.setUserTypeId(userTypeId);
            e.setStrategyTypeId(smallStrategyId);
            list.add(e);
        }
        this.saveBatch(list);
    }

    @Override
    public void delRelStrategy(Long userTypeId,Long smallStrategyId) {
        Integer count = smallStrategyService.countBySmallStrategyId(smallStrategyId);
        if (count>0){
            throw  new ApiException("该用户等级包含的策略正在运行,无法删除(若要删除请先删除策略！)");
        }
        baseMapper.delById(userTypeId,smallStrategyId);
    }

    @Override
    public void delRelBySmallStrategyId(Long smallStrategyId) {
        baseMapper.delRelBySmallStrategyId(smallStrategyId);
    }
}
