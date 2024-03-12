package com.mzwise.modules.quant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.PlatformEnum;
import com.mzwise.modules.quant.entity.QuantApiAccess;
import com.mzwise.modules.quant.mapper.QuantApiAccessMapper;
import com.mzwise.modules.quant.service.AdminQuantApiService;
import com.mzwise.modules.quant.vo.AdminQuantApiAccessVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/09
 */
@Service
public class AdminQuantApiServiceImpl implements AdminQuantApiService {
    @Autowired
    private QuantApiAccessMapper quantApiAccessMapper;

    @Override
    public Page<AdminQuantApiAccessVO> listAllApiSelective(
            String nickname, String phone, String email,
            PlatformEnum platform,
            Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<AdminQuantApiAccessVO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<QuantApiAccess> wrapper = new QueryWrapper<>();
        wrapper.ge("qaa.id", 1);
        if (platform != null) {
            wrapper.lambda().eq(QuantApiAccess::getPlatform, platform);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(QuantApiAccess::getCreateTime, beginDate, endDate);
        }
        return quantApiAccessMapper.listAllApiSelective(page, nickname, phone, email, wrapper);
    }
}
