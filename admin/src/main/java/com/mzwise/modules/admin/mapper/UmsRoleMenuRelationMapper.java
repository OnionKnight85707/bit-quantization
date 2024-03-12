package com.mzwise.modules.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.admin.entity.UmsRoleMenuRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author macro
 * @since 2020-08-21
 */
public interface UmsRoleMenuRelationMapper extends BaseMapper<UmsRoleMenuRelation> {

    /**
     * 根据权限ID 查询全部 菜单ID
     * @param roleId
     * @return
     */
    List<Long> getAllMenuIdByRoleId(@Param("roleId") Long roleId);

}
