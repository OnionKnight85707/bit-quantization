package com.mzwise.modules.home.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.AdminLogActionEnum;
import com.mzwise.constant.AdminLogModuleEnum;
import com.mzwise.constant.CarousePositionTypeEnum;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.service.UmsAdminLogService;
import com.mzwise.modules.home.dto.HomeCarouselPicParam;
import com.mzwise.modules.home.entity.HomeCarouselPic;
import com.mzwise.modules.home.service.HomeCarouselPicService;
import com.mzwise.modules.ucenter.dto.UcPartnerParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Administrator
 * @Date 2021/02/02
 */
@Api(tags = "后台轮播图管理")
@RestController
@RequestMapping("/admin/carousePic")
public class AdminCarousePicController {
    @Autowired
    private HomeCarouselPicService carouselPicService;
    @Autowired
    private UmsAdminLogService adminLogService;

    @ApiOperation("创建首页轮播图")
    @PostMapping("/create")
    public CommonResult create(@RequestBody HomeCarouselPicParam homeCarouselPicParam) {
        carouselPicService.create(homeCarouselPicParam);
        // 记录日志
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.ADD.getValue(), AdminLogModuleEnum.CAROUSEL_MANAGE.getValue(),
                null, JSON.toJSONString(homeCarouselPicParam), "轮播图管理-增加"));
        return CommonResult.success();
    }

    public static void main(String[] args) {
        new String("test");
    }

    @ApiOperation("修改首页轮播图")
    @PutMapping("/update")
    public CommonResult update(@RequestBody HomeCarouselPic homeCarouselPic) {
        carouselPicService.updateById(homeCarouselPic);
        // 记录日志
        HomeCarouselPic carouselPic = new HomeCarouselPic();
        HomeCarouselPic byId = carouselPicService.getById(homeCarouselPic.getId());
        BeanUtils.copyProperties(byId, carouselPic);
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.CAROUSEL_MANAGE.getValue(),
                JSON.toJSONString(carouselPic), JSON.toJSONString(homeCarouselPic), "轮播图管理-修改"));
        return CommonResult.success();
    }

    @ApiOperation("修改展示与否")
    @PutMapping("/isShow/{id}")
    public CommonResult updateIsShow(@PathVariable(value = "id") Long homeCarousePicId) {
        carouselPicService.updateIsShow(homeCarousePicId);
        // 记录日志
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.CAROUSEL_MANAGE.getValue(),
                null, JSON.toJSONString(homeCarousePicId), "轮播图管理-修改"));
        return CommonResult.success();
    }

    @ApiOperation("删除首页轮播图")
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        carouselPicService.removeById(id);
        // 记录日志
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.DELETE.getValue(), AdminLogModuleEnum.CAROUSEL_MANAGE.getValue(),
                null, JSON.toJSONString(id), "轮播图管理-删除"));
        return CommonResult.success();
    }

    @ApiOperation("多选删除首页轮播图")
    @DeleteMapping("/deleteBatch")
    public CommonResult deleteBatch(@RequestBody Long[] ids) {
        carouselPicService.deleteBatch(ids);
        // 记录日志
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.DELETE.getValue(), AdminLogModuleEnum.CAROUSEL_MANAGE.getValue(),
                null, JSON.toJSONString(ids), "轮播图管理-删除"));
        return CommonResult.success();
    }

    @ApiOperation("后台分页展示多语言 轮播图")
    @GetMapping("/list")
    public CommonResult<Page<HomeCarouselPic>> listCarousePic(@RequestParam(required = false) String language,
                                                              @RequestParam(required = false) String name,
                                                              @RequestParam(required = false) Boolean isShow,
                                                              @RequestParam(required = false) CarousePositionTypeEnum position,
                                                              @RequestParam(defaultValue = "1") Integer pageNum,
                                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        return CommonResult.success(carouselPicService.listPage(language, name, isShow, position, pageNum, pageSize));
    }

}
