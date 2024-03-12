package com.mzwise.modules.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.admin.entity.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author macro
 * @since 2020-08-21
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
     * 获取用户所有角色
     */
    List<UmsRole> getRoleList(@Param("adminId") Long adminId);

    /**
     * 角色列表
     * @param page 分页
     * @param keyword 搜索关键字
     * @return
     */
    Page<UmsRole> roleList(Page<UmsRole> page, @Param("keyword") String keyword);

}
