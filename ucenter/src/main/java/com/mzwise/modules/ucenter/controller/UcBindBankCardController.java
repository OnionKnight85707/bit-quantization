package com.mzwise.modules.ucenter.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.ucenter.dto.UcBindBankCardParam;
import com.mzwise.modules.ucenter.entity.UcBindBankCard;
import com.mzwise.modules.ucenter.service.UcBindBankCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Api(tags = "绑定银行卡")
@RestController
@RequestMapping("/ucenter/ucBindBankCard")
public class UcBindBankCardController {
    @Autowired
    private UcBindBankCardService bindBankCardService;

    @ApiOperation("/绑定银行卡")
    @PostMapping("/bindBankCard")
    public CommonResult bindBankCard(@RequestBody UcBindBankCardParam bindBankCardParam) {
        bindBankCardService.bindBankCard(bindBankCardParam, SecurityUtils.getCurrentUserId());
        return CommonResult.success();
    }

    @ApiOperation("/删除银行卡")
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Integer id) {
        bindBankCardService.removeById(id);
        return CommonResult.success();
    }

    @ApiOperation("/绑定的银行卡列表")
    @GetMapping("/listMyCards")
    public CommonResult<Page<UcBindBankCard>> listMyCards(@RequestParam(defaultValue = "1") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        return CommonResult.success(bindBankCardService.listMyCards(SecurityUtils.getCurrentUserId(), page, size));
    }

    @ApiOperation("/看某个绑定的银行卡详情")
    @GetMapping("/detail")
    public CommonResult<UcBindBankCard> getDetail(@RequestParam Long bindCardId) {
        return CommonResult.success(bindBankCardService.getById(bindCardId));
    }
}

