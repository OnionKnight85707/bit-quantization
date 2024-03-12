package com.mzwise.modules.home.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.util.LocaleSourceUtil;
import com.mzwise.constant.IntroductionTypeEnum;
import com.mzwise.modules.home.dto.HomeIntroductionParam;
import com.mzwise.modules.home.entity.HomeIntroduction;
import com.mzwise.modules.home.mapper.HomeIntroductionMapper;
import com.mzwise.modules.home.service.HomeIntroductionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Service
public class HomeIntroductionServiceImpl extends ServiceImpl<HomeIntroductionMapper, HomeIntroduction> implements HomeIntroductionService {
    @Override
    public void create(HomeIntroductionParam introductionParam) {
        HomeIntroduction introduction = new HomeIntroduction();
        BeanUtils.copyProperties(introductionParam, introduction);
        save(introduction);
    }

    @Override
    public Page<HomeIntroduction> listAll(IntroductionTypeEnum type, String language, String title, Integer pageNum, Integer pageSize) {
        Page<HomeIntroduction> page = new Page<>(pageNum, pageSize);
        QueryWrapper<HomeIntroduction> wrapper = new QueryWrapper<>();
        if (type!=null) {
            wrapper.lambda().eq(HomeIntroduction::getType, type);
        }
        if (StringUtils.isNotBlank(language)) {
            wrapper.lambda().eq(HomeIntroduction::getLanguage, language);
        }
        if (StringUtils.isNotBlank(title)) {
            wrapper.lambda().like(HomeIntroduction::getTitle, title);
        }
        return page(page, wrapper);
    }

    @Override
    public HomeIntroduction getByTitleAndLanguage(String title) {
        String language = LocaleSourceUtil.getLanguage();
        QueryWrapper<HomeIntroduction> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(HomeIntroduction::getLanguage, language)
                .eq(HomeIntroduction::getTitle, title);
        return getOne(wrapper);
    }
}
