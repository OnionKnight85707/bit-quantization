package com.mzwise.modules.ucenter.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcBindBankCard;
import com.mzwise.modules.ucenter.entity.UcTransaction;
import com.mzwise.modules.ucenter.service.UcTransactionService;
import com.mzwise.modules.ucenter.vo.NameAndValueVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
@Api(tags = "资产明细")
@RestController
@RequestMapping("/ucenter/transaction")
public class UcTransactionController {

    @Autowired
    private UcTransactionService ucTransactionService;

    @ApiOperation(value = "资产明细列表", notes = "@ApiModelProperty(\"保证金\")\n" +
            "    PRINCIPLE(0, \"PRINCIPLE\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"退回保证金\")\n" +
            "    RETURN_PRINCIPLE(1, \"RETURN_PRINCIPLE\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"手续费\")\n" +
            "    FEE(2, \"FEE\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"收益\")\n" +
            "    PROFIT(3, \"PROFIT\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"划转\")\n" +
            "    TRANSFER(4, \"TRANSFER\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"兑换\")\n" +
            "    EXCHANGE(5, \"EXCHANGE\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"锁仓服务费\")\n" +
            "    FROZEN_QUANT_SERVICE(6, \"FROZEN_QUANT_SERVICE\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"入金\")\n" +
            "    RECHARGE(8, \"RECHARGE\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"出金\")\n" +
            "    WITHDRAWAL(9, \"WITHDRAWAL\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"出金审核失败退款\")\n" +
            "    WITHDRAWAL_FAILED_REFUND(10, \"WITHDRAWAL_FAILED_REFUND\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"管理员充值\")\n" +
            "    ADMIN_DEPOSIT(11, \"ADMIN_DEPOSIT\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"管理员扣减\")\n" +
            "    ADMIN_WITHDRAWAL(12, \"ADMIN_WITHDRAWAL\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"分享收益\")\n" +
            "    SHARE_PROFIT(13, \"SHARE_PROFIT\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"社区奖励\")\n" +
            "    COMMUNITY_AWARD(14, \"SHARE_PROFIT\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"股东/董事 分红\")\n" +
            "    DIVIDENDS(15, \"DIVIDENDS\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"社区量化分红\")\n" +
            "    QUANTITATIVE_PLATFORM_CURRENCY_DIVIDENDS(16, \"QUANTITATIVE_PLATFORM_CURRENCY_DIVIDENDS\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"扫码转账\")\n" +
            "    SCAN_CODE_TRANSFER(17, \"SCAN_CODE_TRANSFER\"),\n" +
            "\n" +
            "    @ApiModelProperty(\"收益服务费\")\n" +
            "    CHARGE_QUANT_SERVICE(18, \"CHARGE_QUANT_SERVICE\");")
    @GetMapping("/list")
    public CommonResult<Page<UcTransaction>> list(
            @RequestParam(required = false) WalletTypeEnum walletType,
            @RequestParam(required = false) TransactionTypeEnum type,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return CommonResult.success(
                ucTransactionService.list(SecurityUtils.getCurrentUserId(), walletType, type, page, size)
        );
    }

    @ApiOperation(value = "资产明细事件列表")
    @GetMapping("/transactionTypeList")
    public CommonResult<List<NameAndValueVo>> transactionTypeList() {
        return CommonResult.success(ucTransactionService.transactionTypeList());
    }

}

