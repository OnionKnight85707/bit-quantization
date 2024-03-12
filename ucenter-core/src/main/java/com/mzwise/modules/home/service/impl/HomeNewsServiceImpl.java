package com.mzwise.modules.home.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.util.LocaleSourceUtil;
import com.mzwise.modules.home.dto.HomeNewsParam;
import com.mzwise.modules.home.entity.HomeNews;
import com.mzwise.modules.home.mapper.HomeNewsMapper;
import com.mzwise.modules.home.service.HomeNewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Service
public class HomeNewsServiceImpl extends ServiceImpl<HomeNewsMapper, HomeNews> implements HomeNewsService {
    @Override
    public void create(HomeNewsParam homeNewsParam) {
        HomeNews news = new HomeNews();
        BeanUtils.copyProperties(homeNewsParam, news);
        save(news);
    }

    @Override
    public HomeNews getDetail(Long newsId) {
        HomeNews news = getById(newsId);
        news.setNumberOfReading(news.getNumberOfReading() + 1);
        updateById(news);
        return news;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<HomeNews> listPage(Integer pageNum, Integer pageSize) {
        Page<HomeNews> page = new Page<>(pageNum, pageSize);
        QueryWrapper<HomeNews> wrapper = new QueryWrapper<>();
        String language = LocaleSourceUtil.getLanguage();
        wrapper.lambda().eq(HomeNews::getLanguage, language).eq(HomeNews::getIsShow, true);
        wrapper.lambda().orderByDesc(HomeNews::getIsTop, HomeNews::getCreateTime);
        return page(page, wrapper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<HomeNews> listPage(String language, String title, Boolean isShow, Integer pageNum, Integer pageSize) {
        Page<HomeNews> page = new Page<>(pageNum, pageSize);
        QueryWrapper<HomeNews> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<HomeNews> lambda = wrapper.lambda();
        if (StringUtils.isNotBlank(language)) {
            lambda.eq(HomeNews::getLanguage, language);
        }
        if (StringUtils.isNotBlank(title)) {
            lambda.like(HomeNews::getTitle, title);
        }
        if (isShow != null) {
            lambda.eq(HomeNews::getIsShow, isShow);
        }
        lambda.orderByDesc(HomeNews::getIsTop, HomeNews::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public void updateIsShow(Long id) {
        HomeNews homeNews = getById(id);
        homeNews.setIsShow(!homeNews.getIsShow());
        updateById(homeNews);
    }

    @Override
    public void updateIsTop(Long id) {
        HomeNews homeNews = getById(id);
        homeNews.setIsTop(!homeNews.getIsTop());
        updateById(homeNews);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        QueryWrapper<HomeNews> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(HomeNews::getId, Arrays.asList(ids));
        remove(wrapper);
    }
}
