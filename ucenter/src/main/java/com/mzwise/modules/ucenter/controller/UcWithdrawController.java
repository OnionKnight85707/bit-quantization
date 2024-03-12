package com.mzwise.modules.ucenter.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.annotation.CustomVerifyRequired;
import com.mzwise.annotation.PayPasswordRequired;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.ucenter.dto.WithdrawParam;
import com.mzwise.modules.ucenter.entity.UcWithdraw;
import com.mzwise.modules.ucenter.service.UcWithdrawService;
import com.mzwise.modules.ucenter.vo.WithDrawOptionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
@Api(tags = "提币")
@RestController
@RequestMapping("/ucenter/withdraw")
public class UcWithdrawController {

    @Autowired
    private UcWithdrawService withdrawService;

    @ApiOperation(value = "提币可选项",
            notes = "symbol为币种\n" +
                    "balance>当前余额\n" +
                    "withdrawScale>提币数量精度(小数点位数)\n" +
                    "withdrawFee>提币手续费率，如提币100，费率0.01，预计到账100-100*0.01=99\n"
    )
    @GetMapping("/options")
    public CommonResult<List<WithDrawOptionVO>> options() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        List<WithDrawOptionVO> options = withdrawService.getOptions(currentUserId);
        return CommonResult.success(options);
    }

    @ApiOperation(value = "提币明细")
    @GetMapping
    public CommonResult<Page<UcWithdraw>> list(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        Long memberId = SecurityUtils.getCurrentUserId();
        Page<UcWithdraw> list = withdrawService.list(memberId, page, size);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "提币申请")
    @PostMapping("/apply")
    @CustomVerifyRequired
    @PayPasswordRequired
    public CommonResult withdrawApply(@RequestBody WithdrawParam param, HttpServletRequest request) {
        Long memberId = SecurityUtils.getCurrentUserId();
        withdrawService.apply(request, memberId, param.getWalletType(), param.getAddress(), param.getAmount());
        return CommonResult.success();
    }
}

