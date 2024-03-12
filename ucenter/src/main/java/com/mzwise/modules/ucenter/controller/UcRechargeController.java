package com.mzwise.modules.ucenter.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.ucenter.entity.UcRecharge;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.modules.ucenter.service.UcRechargeService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
@Api(tags = "充币")
@RestController
@RequestMapping("/ucenter/recharge")
public class UcRechargeController {

    @Autowired
    private UcRechargeService rechargeService;

    /**
     * 重置地址
     *
     * @param symbol
     * @return
     */
    @ApiOperation(value = "获取充币地址", notes = "接口时间较长，如果用户钱包已有充币地址不需要调用此接口重新获取")
    @GetMapping("/reset-address")
    public CommonResult resetWalletAddress(HttpServletRequest request, String symbol) {
        String address = rechargeService.createCoinAddress(SecurityUtils.getCurrentUserId(), request, symbol);
        return CommonResult.success(address);
    }

    @ApiOperation(value = "充币明细")
    @GetMapping
    public CommonResult<Page<UcRecharge>> list(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        Long memberId = SecurityUtils.getCurrentUserId();
        Page<UcRecharge> list = rechargeService.list(memberId, page, size);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "充币可选项",
            notes = "symbol为币种\n" +
                    "address为充币地址\n " +
                    "二维码前端负责生成"
    )
    @GetMapping("/options")
    public CommonResult<List<UcWallet>> options() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        List<UcWallet> options = rechargeService.getOptions(currentUserId);
        return CommonResult.success(options);
    }

}

