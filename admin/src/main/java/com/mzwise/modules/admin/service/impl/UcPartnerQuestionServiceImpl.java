package com.mzwise.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.admin.entity.UcPartnerQuestion;
import com.mzwise.modules.admin.mapper.UcPartnerQuestionMapper;
import com.mzwise.modules.admin.service.UcPartnerQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-07
 */
@Service
@Slf4j
public class UcPartnerQuestionServiceImpl extends ServiceImpl<UcPartnerQuestionMapper, UcPartnerQuestion> implements UcPartnerQuestionService {


    @Override
    public List<UcPartnerQuestion> findAll()
    {
        return  baseMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public void delete(Integer id)
    {
        baseMapper.deleteById(id);
    }


    @Override
    public void  edit(UcPartnerQuestion uc)
    {
        updateById(uc);
    }
}
