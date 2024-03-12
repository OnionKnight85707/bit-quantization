package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.util.IpUtils;
import com.mzwise.constant.TemplateStopModeEnum;
import com.mzwise.modules.ucenter.dto.UniSmallStrategyParam;
import com.mzwise.modules.ucenter.entity.UniBigStrategy;
import com.mzwise.modules.ucenter.entity.UniSmallStrategy;
import com.mzwise.modules.ucenter.entity.UniTemplate;
import com.mzwise.modules.ucenter.mapper.UniSmallStrategyMapper;
import com.mzwise.modules.ucenter.service.UniBigStrategyService;
import com.mzwise.modules.ucenter.service.UniRelStrategyService;
import com.mzwise.modules.ucenter.service.UniSmallStrategyService;
import com.mzwise.modules.ucenter.service.UniTemplateService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UniSmallStrategyServiceImpl extends ServiceImpl<UniSmallStrategyMapper, UniSmallStrategy> implements UniSmallStrategyService {

    @Autowired
    private UniBigStrategyService bigStrategyService;

    @Autowired
    UniTemplateService templateService;

    @Autowired
    private UniRelStrategyService relStrategyService;


    private String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    /**
     *  查询小类策略列表
     * @return
     */
    @Override
    public List<UniSmallStrategy> queryUniSmallStrategy() {
        return list();
    }

    /**
     *  新增小类策略
     * @param param
     * @return
     */
    @Override
    public UniSmallStrategy addUniSmallStrategy(UniSmallStrategyParam param) {
        Integer bigStrategyId = param.getBigStrategyId();
        UniBigStrategy BigStrategy = bigStrategyService.getById(bigStrategyId);
        Assert.notNull(BigStrategy,"没有该大类策略，请输入正确的大类策略编号");
        Integer templateId = param.getTemplateId();

        UniTemplate template = templateService.getOneById(templateId);

        Assert.notNull(template,"选择的参数模板不存在，请重新操作！");
        UniSmallStrategy smallStrategy = new UniSmallStrategy();
        BeanUtils.copyProperties(param,smallStrategy);
        // 新增小类策略自动生成token
        String token = RandomStringUtils.randomAlphanumeric(16);
        if(template.getOpenCloseMode().equals(TemplateStopModeEnum.EXTERNAL_SIGNAL)){
            String[]  mask={"0","8","16","24","32"};
            smallStrategy.setToken(token);
            if ( ! "0.0.0.0".equals(param.getIp())) {
                String str1 = param.getIp().substring(0, param.getIp().length());
                String[] result = str1.split(",");
                for (String ip:result){
                    if(!Pattern.matches(regex,ip) && !"0.0.0.0".equals(ip)){
                        throw new ApiException("存在不可用IP地址，请输入全部有效的IP地址");
                    }
                }
            }
            smallStrategy.setIp(param.getIp());
            String str2 = param.getMask().substring(0, param.getMask().length());
            String[] result2 =str2.split(",");
            for (String m:result2) {
                if (!Arrays.asList(mask).contains(m) ){
                    throw new ApiException("存在不可用掩码,请输入有效的掩码(0,8,16,24,32)");
                }
            }
            smallStrategy.setMask(param.getMask());
        }
        save(smallStrategy);
        return smallStrategy;
    }

    /**
     *  修改小类策略
     * @param strategy
     */
    @Override
    public void updateUniSmallStrategy(UniSmallStrategy strategy) {
        String token = RandomStringUtils.randomAlphanumeric(16);
        UpdateWrapper<UniSmallStrategy> wrapper=new UpdateWrapper<>();
        UniTemplate template = templateService.getOneById(strategy.getTemplateId());
        String[]  mask={"0","8","16","24","32"};
        LambdaUpdateWrapper<UniSmallStrategy> updateWrapper = wrapper.lambda().eq(UniSmallStrategy::getId, strategy.getId())
                .set(UniSmallStrategy::getSymbolList, strategy.getSymbolList())
                .set(UniSmallStrategy::getBigStrategyId, strategy.getBigStrategyId())
                .set(UniSmallStrategy::getName, strategy.getName())
                .set(UniSmallStrategy::getRisk, strategy.getRisk())
                .set(UniSmallStrategy::getTemplateId, strategy.getTemplateId())
                .set(UniSmallStrategy::getUrl, strategy.getUrl());
        if(template.getOpenCloseMode().equals(TemplateStopModeEnum.EXTERNAL_SIGNAL)){
            updateWrapper.set(UniSmallStrategy::getSort,strategy.getSort());
            if(!"0.0.0.0".equals(strategy.getIp())){
                String str1 = strategy.getIp().substring(0, strategy.getIp().length());
                String[] result = str1.split(",");
                for (String ip:result){
                    if(!Pattern.matches(regex,ip) && !"0.0.0.0".equals(ip)){
                        throw new ApiException("存在不可用IP地址，请输入全部有效的IP地址");
                    }
                }
            }
            String str2 = strategy.getMask().substring(0, strategy.getMask().length());
            String[] result2 =str2.split(",");
            for (String m:result2) {
                if (!Arrays.asList(mask).contains(m) ){
                    throw new ApiException("存在不可用掩码,请输入有效的掩码(0,8,16,24,32)");
                }
            }
            updateWrapper.set(UniSmallStrategy::getIp,strategy.getIp()).set(UniSmallStrategy::getMask,strategy.getMask());
        }else {
            updateWrapper.set(UniSmallStrategy::getSort,strategy.getSort());
        }
        update(wrapper);
    }

    @Override
    public UniSmallStrategy getByName(String name) {
        return baseMapper.getByName(name);
    }

    @Override
    public boolean checkIp(String token, String ip) {
        UniSmallStrategy strategy= baseMapper.getByToken(token);
        if (strategy==null)
            return false;

        if("0.0.0.0".equals(strategy.getIp()))
        {
            return true;
        }

        String []ips=strategy.getIp().split(",");

        String[] masks= strategy.getMask().split(",");

        for (int i=0;i< ips.length;i++)
        {
            String ipRule=ips[i];
            String mask=masks[i];

            if (IpUtils.isInRange(ip,ipRule+"/"+mask))
            {
                return  true;
            }
        }


        return false;
    }

    @Override
    public Integer countByTemplateId(Long templateId) {
        return baseMapper.countByTemplateId(templateId);
    }

    /**
     *  根据id删除小策略
     * @param id
     */
    @Override
    public void delSmallStrategy(Long id) {
        Integer count = countBySmallStrategyId(id);
        if(count>0){
            throw new ApiException("该小策略关联到某个策略，无法删除(若要删除请先删除策略!)");
        }
        baseMapper.delSmallStrategy(id);
        // 删除小策略之后 删除其关联关系
        relStrategyService.delRelBySmallStrategyId(id);
    }


    @Override
    public Integer countBySmallStrategyId(Long smallStrategyId) {
        return baseMapper.countBySmallStrategyId(smallStrategyId);
    }

    @Override
    public Integer countByBigStrategyId(Long bigStrategyId) {
        return baseMapper.countByBigStrategyId(bigStrategyId);
    }

    @Override
    public Integer getOutSignalType(String token) {
        return baseMapper.getOutSignalType(token);
    }
}
