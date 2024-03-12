package com.mzwise.modules.ucenter.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.AdminLogActionEnum;
import com.mzwise.constant.AdminLogModuleEnum;
import com.mzwise.constant.AnnouncementTypeEnum;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.service.UmsAdminLogService;
import com.mzwise.modules.ucenter.dto.UcAnnouncementParam;
import com.mzwise.modules.ucenter.dto.UcPartnerParam;
import com.mzwise.modules.ucenter.entity.UcAnnouncement;
import com.mzwise.modules.ucenter.service.UcAnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Administrator
 * @Date 2021/02/02
 */
@Api(tags = "后台公告管理")
@RestController
@RequestMapping("/admin/announcement")
public class AdminAnnouncementController {
    @Autowired
    private UcAnnouncementService announcementService;
    @Autowired
    private UmsAdminLogService adminLogService;

    @ApiOperation("新增公告")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UcAnnouncementParam announcementParam) {
        announcementService.create(announcementParam);
        // 记录日志
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.ADD.getValue(), AdminLogModuleEnum.ANNOUNCEMENT_MANAGE.getValue(),
                null, JSON.toJSONString(announcementParam), "公告管理-增加"));
        return CommonResult.success();
    }

    @ApiOperation("修改公告")
    @PutMapping("/update")
    public CommonResult update(@RequestBody UcAnnouncement announcement) {
        announcementService.updateById(announcement);
        // 记录日志
        UcAnnouncement ucAnnouncement = new UcAnnouncement();
        UcAnnouncement byId = announcementService.getById(announcement.getId());
        BeanUtils.copyProperties(byId, ucAnnouncement);
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.ANNOUNCEMENT_MANAGE.getValue(),
                JSON.toJSONString(ucAnnouncement), JSON.toJSONString(announcement), "公告管理-修改"));
        return CommonResult.success();
    }

    @ApiOperation("修改是否展示")
    @PutMapping("/isShow/{id}")
    public CommonResult updateIsShow(@PathVariable(value = "id") Long announcementId) {
        announcementService.updateIsShow(announcementId);
        return CommonResult.success();
    }

    @ApiOperation("修改是否置顶")
    @PutMapping("/isTop/{id}")
    public CommonResult updateIsTop(@PathVariable(value = "id") Long announcementId) {
        announcementService.updateIsTop(announcementId);
        return CommonResult.success();
    }

    @ApiOperation("删除公告")
    @DeleteMapping("delete/{id}")
    public CommonResult delete(@PathVariable("id") Long announcementId) {
        announcementService.removeById(announcementId);
        // 记录日志
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.DELETE.getValue(), AdminLogModuleEnum.ANNOUNCEMENT_MANAGE.getValue(),
                null, JSON.toJSONString(announcementId), "公告管理-删除"));
        return CommonResult.success();
    }

    @ApiOperation("多选删除")
    @DeleteMapping("/deleteBatch")
    public CommonResult deleteBatch(@RequestBody Long[] announcementIds) {
        announcementService.deleteBatch(announcementIds);
        // 记录日志
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.DELETE.getValue(), AdminLogModuleEnum.ANNOUNCEMENT_MANAGE.getValue(),
                null, JSON.toJSONString(announcementIds), "公告管理-删除"));
        return CommonResult.success();
    }

    @ApiOperation(value = "获取所有公告列表")
    @GetMapping("/list")
    public CommonResult<Page<UcAnnouncement>> listAll(@RequestParam(required = false) String language,
                                                      @RequestParam(required = false) String title,
                                                      @RequestParam(required = false) AnnouncementTypeEnum type,
                                                      @RequestParam(required = false) Boolean isShow,
                                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        return CommonResult.success(announcementService.listPage(language, title, type, isShow, pageNum, pageSize));
    }

    @ApiOperation(value = "获取公告详情")
    @GetMapping("/detail")
    public CommonResult<UcAnnouncement> getDetail(@RequestParam Integer announcementId) {
        return CommonResult.success(announcementService.getById(announcementId));
    }


}
