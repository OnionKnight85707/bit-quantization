package com.mzwise.modules.home.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.constant.IntroductionTypeEnum;
import com.mzwise.modules.home.dto.HomeIntroductionParam;
import com.mzwise.modules.home.entity.HomeIntroduction;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface HomeIntroductionService extends IService<HomeIntroduction> {
    /**
     * 新增说明
     *
     * @param introductionParam
     */
    void create(HomeIntroductionParam introductionParam);

    /**
     * 分页展示所有说明
     *
     * @param type
     * @param language
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<HomeIntroduction> listAll(IntroductionTypeEnum type, String language, String title, Integer pageNum, Integer pageSize);

    /**
     * 根据语言和标题获得唯一说明
     *
     * @param title
     * @return
     */
    HomeIntroduction getByTitleAndLanguage(String title);
}
