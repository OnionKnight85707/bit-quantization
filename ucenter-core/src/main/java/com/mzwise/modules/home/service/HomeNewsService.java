package com.mzwise.modules.home.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.home.dto.HomeNewsParam;
import com.mzwise.modules.home.entity.HomeNews;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface HomeNewsService extends IService<HomeNews> {
    /**
     * 创建新闻
     * @param homeNewsParam
     */
    void create(HomeNewsParam homeNewsParam);

    /**
     * 获取新闻详情
     * @param newsId
     * @return
     */
    HomeNews getDetail(Long newsId);

    /**
     * 分页返回新闻列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<HomeNews> listPage(Integer pageNum, Integer pageSize);

    /**
     * 后台条件查询 分页展示新闻列表
     * @param language
     * @param title
     * @param isShow
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<HomeNews> listPage(String language, String title, Boolean isShow, Integer pageNum, Integer pageSize);

    /**
     * 修改是否展示状态
     * @param id
     */
    void updateIsShow(Long id);

    /**
     * 修改是否置顶状态
     * @param id
     */
    void updateIsTop(Long id);

    /**
     * 多选删除
     * @param ids
     */
    void deleteBatch(Long[] ids);
}
