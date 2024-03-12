package com.mzwise.modules.admin.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.admin.entity.UmsResource;
import com.mzwise.modules.ucenter.vo.ResourceVO;

import java.util.List;

/**
 * 后台资源管理Service
 * Created by admin on 2020/2/2.
 */
public interface UmsResourceService extends IService<UmsResource> {
    /**
     * 添加资源
     *
     * @param umsResource
     * @return
     */
    boolean create(UmsResource umsResource);

    /**
     * 修改资源
     *
     * @param id
     * @param umsResource
     * @return
     */
    boolean update(Long id, UmsResource umsResource);

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 分页查询资源
     *
     * @param categoryId
     * @param nameKeyword
     * @param urlKeyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    Page<ResourceVO> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageNum, Integer pageSize);

    /**
     * 查询不隐藏的资源
     *
     * @return
     */
    List<UmsResource> listByHidden();
}
