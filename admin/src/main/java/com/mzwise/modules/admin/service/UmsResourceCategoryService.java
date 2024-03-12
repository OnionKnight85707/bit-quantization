package com.mzwise.modules.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.admin.entity.UmsResourceCategory;

import java.util.List;

/**
 * 后台资源分类管理Service
 * Created by admin on 2020/2/5.
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {

    /**
     * 获取所有资源分类
     *
     * @return
     */
    List<UmsResourceCategory> listAll();

    /**
     * 创建资源分类
     *
     * @param umsResourceCategory
     * @return
     */
    boolean create(UmsResourceCategory umsResourceCategory);
}
