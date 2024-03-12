package com.mzwise.modules.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.admin.dto.AdminLogListParam;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.service.UmsAdminLogService;
import com.mzwise.modules.admin.vo.AdminLogListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理员日志控制器
 * @author: David Liang
 * @create: 2022-07-21 21:33
 */
@Api(tags = "后台管理员日志控制器")
@RestController
@RequestMapping("/adminLog")
public class UmsAdminLogController {

    @Autowired
    private UmsAdminLogService adminLogService;

    @ApiOperation(value = "管理员日志列表")
    @PostMapping("/adminLogList")
    public CommonResult<Page<AdminLogListVo>> adminLogList(@RequestBody AdminLogListParam param) {
        Page<AdminLogListVo> page = adminLogService.adminLogList(param);
        return CommonResult.success(page);
    }

}
