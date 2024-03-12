package com.mzwise.modules.home.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.ArticleTypeEnum;
import com.mzwise.modules.home.dto.AdminArticleParam;
import com.mzwise.modules.home.entity.HomeArticle;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2021-05-20
 */
public interface HomeArticleService extends IService<HomeArticle> {

    /**
     * 展示某个分类的文章
     *
     * @param language
     * @param type
     * @return
     */
    List<HomeArticle> list(String language, ArticleTypeEnum type, String keyword, Boolean top);

    /**
     * 根据类型和标题查找
     *
     * @param language
     * @param type
     * @param keyword
     * @return
     */
    HomeArticle getByTypeAndTitle(String language, ArticleTypeEnum type, String keyword);

    /**
     * 创建新闻财经
     *
     * @param param
     */
    void create(AdminArticleParam param);

    /**
     * 后台分页条件查询新闻财经
     *
     * @param language
     * @param type
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<HomeArticle> listArticleSelective(String language, ArticleTypeEnum type, String title, Boolean top, Integer pageNum, Integer pageSize);
}
