package com.mzwise.modules.ucenter.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.exception.ApiException;
import com.mzwise.constant.LoopModeEnum;
import com.mzwise.constant.PositionTypeEnum;
import com.mzwise.constant.SettleTypeEnum;
import com.mzwise.constant.TemplateFirstTypeEnum;
import com.mzwise.modules.ucenter.dto.UniTemplateParam;
import com.mzwise.modules.ucenter.entity.UniTemplate;
import com.mzwise.modules.ucenter.mapper.UniTemplateMapper;
import com.mzwise.modules.ucenter.service.DictionaryService;
import com.mzwise.modules.ucenter.service.UcPartnerLevelSerivce;
import com.mzwise.modules.ucenter.service.UniSmallStrategyService;
import com.mzwise.modules.ucenter.service.UniTemplateService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Date;


@Service
public class UniTemplateServiceImpl extends ServiceImpl<UniTemplateMapper,UniTemplate> implements UniTemplateService {

    @Autowired
    private UniSmallStrategyService smallStrategyService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private UcPartnerLevelSerivce ucPartnerLevelSerivce;

    @Override
    public List<UniTemplate> queryUniTemplate() {
        return list();
    }

    /**
     * 新增模板
     *
     * @param param
     */
    @Override
    public void addTemplate(UniTemplateParam param) {
        SameCode(param);
        UniTemplate template = new UniTemplate();
        BeanUtils.copyProperties(param, template);
        PositionTypeEnum type = param.getPositionType();
        sameModify(param, template);
        template.setSettleType(param.getSettleType());
        template.setId(null);
        template.setCreateTime(new Date());
//        baseMapper.insert(template);
        baseMapper.insertSelective(template);
    }

    // 检查参数
    private void SameCode(UniTemplateParam param) {
        DecimalFormat df=new DecimalFormat("0.00%");
        UniTemplate.checkFirstType(param.getFirstType(), param.getFirstValue());
        UniTemplate.checkStopMode(param.getOpenCloseMode(), param.getStopParam());
        UniTemplate.checkCover(param.getCoverTimes(), param.getCoverParam());
        UniTemplate.checkInputNumber(param.getLeverage(),param.getPosition());
        if (param.getSettleType()==SettleTypeEnum.BY_FREEZE){
        UniTemplate.checkFrozenNumber(param.getExpectedRatio(),param.getCloseRatio(),param.getFrozenRatio());
            BigDecimal partnerMaxCommissionRatio = dictionaryService.getPartnerMaxCommissionRatio();
            BigDecimal levelCommissionRate = ucPartnerLevelSerivce.levelCommissionRate();
            BigDecimal sum = partnerMaxCommissionRatio.add(levelCommissionRate);
            if (param.getFrozenRatio().compareTo(sum)<0){
                throw new ApiException("冻结比例不能小于"+df.format(sum));
            }
        }

    }

    /**
     * 修改模板
     *
     * @param param
     */
    @Override
    public void updateTemplate(UniTemplateParam param) {
        SameCode(param);
        UniTemplate template = new UniTemplate();
        BeanUtils.copyProperties(param, template);
        sameModify(param, template);
//        baseMapper.updateById(template);
        baseMapper.updateByPrimaryKeySelective(template);
    }

    private void sameModify(UniTemplateParam param, UniTemplate template) {
        if(param.getPositionType() == PositionTypeEnum.BY_WALLET){
            template.setPosition(new BigDecimal(0));
        }
        if(param.getSettleType() == SettleTypeEnum.BY_FREEZE){
            template.setExpectedRatio(param.getExpectedRatio());
            template.setCloseRatio(param.getCloseRatio());
            template.setLoopMode(param.getLoopMode());
            template.setFrozenRatio(param.getFrozenRatio());
        }else {
            template.setExpectedRatio(new BigDecimal(0));
            template.setCloseRatio("0");
            template.setLoopMode(LoopModeEnum.LOOP_ONCE);
            template.setFrozenRatio(new BigDecimal(0));
        }
    }

    /**
     * 模板列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<UniTemplate> templateList(Integer pageNum, Integer pageSize) {
        Page<UniTemplate> pageParam = new Page<>(pageNum, pageSize);
//        return page(pageParam);
        return baseMapper.list(pageParam);
    }

    /**
     * 模板列表
     *
     * @return
     */
    @Override
    public List<UniTemplate> UniTemplateList() {
        Page<UniTemplate> pageParam = new Page<>(1, 100);
        return baseMapper.list(pageParam).getRecords();
    }

    @Override
    public UniTemplate getOneById(Integer id) {
        return baseMapper.getOneById(id);
    }

    @Override
    public void deleteByTemplateId(Long id) {
         baseMapper.deleteByTemplateId(id);
    }

    @Override
    public void deleteTemplate(Long templateId) {
        Integer count = smallStrategyService.countByTemplateId(templateId);
        if(count>0){
            throw new ApiException("该模板关联到某个小策略，无法删除(若要删除请先删除小策略!)");
        }
        deleteByTemplateId(templateId);
    }

}
