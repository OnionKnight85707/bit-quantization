package com.mzwise.modules.ucenter.controller;

import com.mzwise.common.api.CommonResult;
import com.mzwise.common.exception.ApiException;
import com.mzwise.modules.ucenter.dto.UcPromotionsSettingParam;
import com.mzwise.modules.ucenter.entity.UcPromotionsSetting;
import com.mzwise.modules.ucenter.service.UcPromotionsSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author 666
 * @Date 2022/07/28
 */
@Api(tags = "优惠活动")
@RestController
@RequestMapping("/ums/promotions-setting")
public class UmsPromotionsSettingController {

    @Autowired
    private UcPromotionsSettingService ucPromotionsSettingService;

    @ApiOperation("查询优惠活动")
    @GetMapping("/query-promotions/{id}")
    public CommonResult<UcPromotionsSetting> queryAllPromotions(@PathVariable Integer id) {
        UcPromotionsSetting promotionsSetting = ucPromotionsSettingService.getById(id);
        return CommonResult.success(promotionsSetting);
    }

    @ApiOperation("修改优惠活动")
    @PostMapping("/modify/promotions")
    public CommonResult<UcPromotionsSetting> modifyPromotionsSetting(@RequestBody UcPromotionsSettingParam param) {
        UcPromotionsSetting setting = new UcPromotionsSetting();
        BeanUtils.copyProperties(param,setting);
        ucPromotionsSettingService.checkTime(new Date(System.currentTimeMillis()), setting.getBeginTime(), setting.getEndTime());
        ucPromotionsSettingService.updateById(setting);
        return CommonResult.success();
    }

}
