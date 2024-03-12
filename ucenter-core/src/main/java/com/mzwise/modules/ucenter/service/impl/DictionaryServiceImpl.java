package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.entity.Dictionary;
import com.mzwise.modules.ucenter.mapper.DictionaryMapper;
import com.mzwise.modules.ucenter.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author 666
 * @since 2022-08-10
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {

    /**
     * 查询最大短信发送条数
     *
     * @return
     */
    @Override
    public Integer queryMaxSendMessage() {
        return Integer.parseInt(baseMapper.queryMaxSendMessage());
    }

    /**
     * 列表查询字典表数据
     *
     * @param explanation
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<Dictionary> listAllDictionary(String explanation, Integer pageNum, Integer pageSize) {
        Page<Dictionary> page = new Page<>(pageNum, pageSize);
        return baseMapper.listAllDictionary(page, explanation);
    }



    @Override
    public BigDecimal getPartnerMaxCommissionRatio() {
        return baseMapper.getPartnerMaxCommissionRatio();
    }
}
