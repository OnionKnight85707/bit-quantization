package com.mzwise.modules.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.admin.dto.AdminLogListParam;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.vo.AdminLogListVo;
import org.apache.ibatis.annotations.Param;

/**
 * 管理员日志映射
 * @author: David Liang
 * @create: 2022-07-21 16:29
 */
public interface UmsAdminLogMapper extends BaseMapper<UmsAdminLog> {

    /**
     * 管理员日志列表
     * @param page
     * @param param
     * @return
     */
    Page<AdminLogListVo> adminLogList(Page<UmsAdminLog> page, @Param("param") AdminLogListParam param);

}
