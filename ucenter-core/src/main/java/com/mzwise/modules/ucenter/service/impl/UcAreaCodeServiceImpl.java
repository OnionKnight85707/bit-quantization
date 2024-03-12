package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.entity.UcAreaCode;
import com.mzwise.modules.ucenter.mapper.UcAreaCodeMapper;
import com.mzwise.modules.ucenter.service.UcAreaCodeService;
import com.mzwise.modules.ucenter.vo.AreaCodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UcAreaCodeServiceImpl extends ServiceImpl<UcAreaCodeMapper, UcAreaCode> implements UcAreaCodeService {

    @Override
    public List<AreaCodeVO> areaCodelist(String language) {
        return baseMapper.areaCodelist(language);
    }

    @Override
    public UcAreaCode findByAreaCode(String areaCode,String language) {
        return baseMapper.selectByAreaCode(areaCode,language);
    }


}
