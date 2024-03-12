package com.mzwise.modules.ucenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.entity.UcMemberAppend;
import com.mzwise.modules.ucenter.mapper.UcMemberAppendMapper;
import com.mzwise.modules.ucenter.service.UcMemberAppendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UcMemberAppendServiceImpl extends ServiceImpl<UcMemberAppendMapper, UcMemberAppend> implements UcMemberAppendService {

    @Override
    public Boolean deleteByMemberId(Long memberId) {
        QueryWrapper<UcMemberAppend> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(UcMemberAppend::getMemberId, memberId);
        return remove(wrapper);
    }

    @Override
    public UcMemberAppend getByMemberId(Long memberId) {
        QueryWrapper<UcMemberAppend> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(UcMemberAppend::getMemberId, memberId);
        return getOne(wrapper);
    }
}
