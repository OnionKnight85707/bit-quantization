package com.mzwise.modules.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.admin.entity.UmsResource;
import com.mzwise.modules.admin.mapper.UmsResourceMapper;
import com.mzwise.modules.admin.service.UmsAdminCacheService;
import com.mzwise.modules.admin.service.UmsResourceService;
import com.mzwise.modules.ucenter.vo.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 后台资源管理Service实现类
 * Created by admin on 2020/2/2.
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements UmsResourceService {
    @Autowired
    private UmsAdminCacheService adminCacheService;
    @Autowired
    private UmsResourceMapper resourceMapper;

    @Override
    public boolean create(UmsResource umsResource) {
        umsResource.setCreateTime(new Date());
        return save(umsResource);
    }

    @Override
    public boolean update(Long id, UmsResource umsResource) {
        umsResource.setId(id);
        boolean success = updateById(umsResource);
        adminCacheService.delResourceListByResource(id);
        return success;
    }

    @Override
    public boolean delete(Long id) {
        boolean success = removeById(id);
        adminCacheService.delResourceListByResource(id);
        return success;
    }

    @Override
    public Page<ResourceVO> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageNum, Integer pageSize) {
        Page<UmsResource> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsResource> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UmsResource> lambda = wrapper.lambda();
        if (categoryId != null) {
            lambda.eq(UmsResource::getCategoryId, categoryId);
        }
        if (StrUtil.isNotEmpty(nameKeyword)) {
            wrapper.like("ur.name", nameKeyword);
        }
        if (StrUtil.isNotEmpty(urlKeyword)) {
            lambda.like(UmsResource::getUrl, urlKeyword);
        }
        return resourceMapper.listAllResources(page, wrapper);
    }

    @Override
    public List<UmsResource> listByHidden() {
        QueryWrapper<UmsResource> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsResource::getHidden, false);
        return list(wrapper);
    }
}
