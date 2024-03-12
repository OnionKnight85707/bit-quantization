package com.mzwise.modules.home.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.constant.CarousePositionTypeEnum;
import com.mzwise.modules.home.dto.HomeCarouselPicParam;
import com.mzwise.modules.home.entity.HomeCarouselPic;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface HomeCarouselPicService extends IService<HomeCarouselPic> {

    /**
     * 创建首页轮播图
     * @param carouselPicParam
     * @return
     */
    void create(HomeCarouselPicParam carouselPicParam);

    /**
     * 展示首页轮播图
     * @return
     */
    List<HomeCarouselPic> showHomeCarousePic(CarousePositionTypeEnum position);

    /**
     * 修改展示与否
     * @param id
     */
    void updateIsShow(Long id);

    /**
     * 后台分页展示多语言 轮播图
     * @param name
     * @param language
     * @param isShow
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<HomeCarouselPic> listPage(String language, String name, Boolean isShow, CarousePositionTypeEnum position, Integer pageNum, Integer pageSize);

    /**
     * 多选删除
     * @param ids
     */
    void deleteBatch(Long[] ids);
}
