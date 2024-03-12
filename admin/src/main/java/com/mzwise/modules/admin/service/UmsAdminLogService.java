package com.mzwise.modules.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.admin.dto.AdminLogListParam;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.vo.AdminLogListVo;

/**
 * 管理员日志接口
 * @author: David Liang
 * @create: 2022-07-21 16:56
 */
public interface UmsAdminLogService extends IService<UmsAdminLog> {

    /**
     * 新增管理员日志
     * @param adminLog
     */
    void addAdminLog(UmsAdminLog adminLog);

    /**
     * 管理员日志列表
     * @param param
     * @return
     */
    Page<AdminLogListVo> adminLogList(AdminLogListParam param);

}
