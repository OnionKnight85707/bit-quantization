package com.mzwise.modules.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.admin.entity.UmsResource;
import com.mzwise.modules.ucenter.vo.ResourceVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author macro
 * @since 2020-08-21
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {

    /**
     * 获取用户所有可访问资源
     *
     * @param adminId
     * @return
     */
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);

    /**
     * 根据角色ID获取资源
     *
     * @param roleId
     * @return
     */
    List<UmsResource> getResourceListByRoleId(@Param("roleId") Long roleId);

    /**
     * 列出所有资源
     *
     * @param page
     * @param wrapper
     * @return
     */
    Page<ResourceVO> listAllResources(Page<UmsResource> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
