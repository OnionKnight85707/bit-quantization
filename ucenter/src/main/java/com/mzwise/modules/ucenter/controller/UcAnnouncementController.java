package com.mzwise.modules.ucenter.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.ucenter.entity.UcAnnouncement;
import com.mzwise.modules.ucenter.service.UcAnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Api(tags = "公告")
@RestController
@RequestMapping("/ucenter/ucAnnouncement")
public class UcAnnouncementController {
    @Autowired
    private UcAnnouncementService announcementService;

    @ApiOperation(value = "获取所有公告列表")
    @GetMapping("/list")
    @AnonymousAccess
    public CommonResult<Page<UcAnnouncement>> listAll(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        return CommonResult.success(announcementService.listByLanguage(page, size));
    }

    @ApiOperation(value = "获取公告详情")
    @GetMapping("/detail")
    @AnonymousAccess
    public CommonResult<UcAnnouncement> getDetail(@RequestParam Integer announcementId) {
        return CommonResult.success(announcementService.getById(announcementId));
    }
}

