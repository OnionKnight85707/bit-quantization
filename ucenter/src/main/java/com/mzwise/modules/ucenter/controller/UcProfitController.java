package com.mzwise.modules.ucenter.controller;


import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.modules.ucenter.service.UcProfitService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import com.mzwise.modules.ucenter.vo.UserProfitGeneralVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-04-01
 */
@Api(tags = "用户收益")
@RestController
@RequestMapping("/ucenter/profit")
public class UcProfitController {

    @Autowired
    private UcProfitService profitService;

    @Autowired
    private UcWalletService walletService;

    @ApiOperation(value = "个人收益概览(包含历史收益和今日收益)")
    @GetMapping("/general")
    public CommonResult<UserProfitGeneralVO> general() {
        Long memberId = SecurityUtils.getCurrentUserId();
        UserProfitGeneralVO userProfitGeneralVO = profitService.general(memberId);
        return CommonResult.success(userProfitGeneralVO);
    }

    @ApiOperation(value = "平台币分红",
            notes = "balance: 余额\n" +
                    "totalProfit: 总收益\n" +
                    "todayProfit: 今日收益\n")
    @GetMapping("/share")
    public CommonResult<UcWallet> share() {
        Long memberId = SecurityUtils.getCurrentUserId();
        UcWallet wallet = walletService.getWallet(memberId, WalletTypeEnum.PLATFORM_SHARE);
        return CommonResult.success(wallet);
    }
}

