package com.mzwise.modules.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.admin.entity.UmsRoleResourceRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台角色资源关系表 Mapper 接口
 * </p>
 *
 * @author macro
 * @since 2020-08-21
 */
public interface UmsRoleResourceRelationMapper extends BaseMapper<UmsRoleResourceRelation> {

    /**
     * 根据权限ID 查询全部 资源ID
     * @param roleId
     * @return
     */
    List<Long> getAllResourceIdByIdRoleId(@Param("roleId") Long roleId);

}
