package com.mzwise.modules.ucenter.controller;


import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.ucenter.dto.ScanCodeTransferParam;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.modules.ucenter.service.UcWalletService;
import com.mzwise.modules.ucenter.service.WalletService;
import com.mzwise.modules.ucenter.vo.BalanceAllTypeVO;
import com.mzwise.modules.ucenter.vo.BalanceVO;
import com.mzwise.modules.ucenter.vo.WalletDetailVO;
import com.mzwise.modules.ucenter.vo.WalletGeneralVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户账户管理
 * </p>
 *
 * @author admin
 * @since 2021-03-30
 */
@Api(tags = "资产管理")
@RestController
@RequestMapping("/ucenter/wallet")
public class UcWalletController {

    @Autowired
    private UcWalletService walletService;
    @Autowired
    private WalletService myWalletService;

    @ApiOperation(value = "首页资产概览", notes = "total：总资产 service：服务费")
    @GetMapping("/general")
    @AnonymousAccess
    public CommonResult<WalletGeneralVO> general() {
        Long currentUserId = null;
        try {
            currentUserId = SecurityUtils.getCurrentUserId();
        } catch (Exception e) {
            if (ObjectUtils.isEmpty(currentUserId)) {
                return CommonResult.success(new WalletGeneralVO(new BalanceVO(), new BalanceVO(),new BalanceVO()));
            }
        }
        BalanceVO totalBalance = walletService.getTotalBalance(currentUserId);
        BalanceVO serviceBalance = walletService.getTotalBalance(currentUserId, WalletTypeEnum.QUANT_SERVICE);
        BalanceVO awardBalance=walletService.getAwardBalance(currentUserId,WalletTypeEnum.QUANT_SERVICE);
        return CommonResult.success(new WalletGeneralVO(totalBalance, serviceBalance,awardBalance));
    }

    @ApiOperation(value = "资产详情", notes = "total总资产\n" +
            "items 各项资产\n " +
            "账户类型 QUANT:资产账户(USDT) MINING:资产账户(FIL) PLATFORM:平台账户(BTE) QUANT_COMMUNITY:量化社区  QUANT_SERVICE:量化服务费 MINING_COMMUNITY:矿机社区 MINING_PROFIT:矿机收益 MINING_PROFIT:矿机收益 PLATFORM_SHARE:平台分红")
    @GetMapping("/detail")
    public CommonResult<WalletDetailVO> detail() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        BalanceVO totalBalance = walletService.getTotalBalance(currentUserId);
        BalanceAllTypeVO allWalletsBalance = walletService.getAllWalletsBalance(currentUserId);
        return CommonResult.success(new WalletDetailVO(totalBalance, allWalletsBalance));
    }

    @ApiOperation(value = "账户资产详情", notes = " 返回单个账户的资产详情 " +
            "账户类型 QUANT:资产账户(USDT) MINING:资产账户(FIL) PLATFORM:平台账户(BTE) QUANT_COMMUNITY:量化社区  QUANT_SERVICE:量化服务费 MINING_COMMUNITY:矿机社区 MINING_PROFIT:矿机收益 MINING_PROFIT:矿机收益 PLATFORM_SHARE:平台分红")
    @GetMapping("/detail/{type}")
    public CommonResult<UcWallet> detail(@PathVariable WalletTypeEnum type) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return CommonResult.success(walletService.getWallet(currentUserId, type));
    }

    @ApiOperation(value = "扫码转账")
    @PostMapping("/scan-code-transfer")
    public CommonResult scanCodeTransfer(@RequestBody ScanCodeTransferParam param) {
        Long fromMemberId = SecurityUtils.getCurrentUserId();
        myWalletService.scanCodeTransfer(fromMemberId, param);
        return CommonResult.success();
    }
}

