package com.mzwise.modules.home.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.ArticleTypeEnum;
import com.mzwise.modules.home.dto.AdminArticleParam;
import com.mzwise.modules.home.entity.HomeArticle;
import com.mzwise.modules.home.mapper.HomeArticleMapper;
import com.mzwise.modules.home.service.HomeArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-05-20
 */
@Service
public class HomeArticleServiceImpl extends ServiceImpl<HomeArticleMapper, HomeArticle> implements HomeArticleService {

    @Override
    public List<HomeArticle> list(String language, ArticleTypeEnum type, String keyword, Boolean top) {
        QueryWrapper<HomeArticle> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(keyword)) {
            wrapper.lambda().like(HomeArticle::getTitle, keyword);
        }
        if (top != null) {
            wrapper.lambda().eq(HomeArticle::getTop, top);
        }
        wrapper.lambda().eq(HomeArticle::getLanguage, language);
        wrapper.lambda().eq(HomeArticle::getType, type);
        return list(wrapper);
    }

    @Override
    public HomeArticle getByTypeAndTitle(String language, ArticleTypeEnum type, String keyword) {
        QueryWrapper<HomeArticle> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(HomeArticle::getType, type)
                .eq(HomeArticle::getLanguage, language)
                .like(HomeArticle::getTitle, keyword);
        return getOne(wrapper);
    }

    @Override
    public void create(AdminArticleParam param) {
        HomeArticle article = new HomeArticle();
        BeanUtils.copyProperties(param, article);
        save(article);
    }

    @Override
    public Page<HomeArticle> listArticleSelective(String language, ArticleTypeEnum type, String title, Boolean top, Integer pageNum, Integer pageSize) {
        Page<HomeArticle> page = new Page<>(pageNum, pageSize);
        QueryWrapper<HomeArticle> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(language)) {
            wrapper.lambda().eq(HomeArticle::getLanguage, language);
        }
        if (StringUtils.isNotBlank(title)) {
            wrapper.lambda().like(HomeArticle::getTitle, title);
        }
        if (type != null) {
            wrapper.lambda().eq(HomeArticle::getType, type);
        }
        if (top != null) {
            wrapper.lambda().eq(HomeArticle::getTop, top);
        }
        wrapper.lambda().orderByDesc(HomeArticle::getTop, HomeArticle::getLanguage);
        return page(page, wrapper);

    }
}
