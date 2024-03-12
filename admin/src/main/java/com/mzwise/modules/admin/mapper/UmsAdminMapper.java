package com.mzwise.modules.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.common.dto.IdAndNameVo;
import com.mzwise.modules.admin.entity.UmsAdmin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author macro
 * @since 2020-08-21
 */
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {

    /**
     * 获取资源相关用户ID列表
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);

    /**
     * 管理员列表
     * @param name
     * @return
     */
    List<IdAndNameVo> adminList(@Param("name") String name);

}
