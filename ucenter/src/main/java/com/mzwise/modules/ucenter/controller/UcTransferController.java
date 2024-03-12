package com.mzwise.modules.ucenter.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.ucenter.dto.TransferParam;
import com.mzwise.modules.ucenter.entity.UcTransfer;
import com.mzwise.modules.ucenter.service.UcTransferService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import com.mzwise.modules.ucenter.vo.TransferOptionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
@Api(tags = "划转")
@RestController
@RequestMapping("/ucenter/transfer")
public class UcTransferController {
    @Autowired
    private UcWalletService walletService;

    @Autowired
    private UcTransferService transferService;

    @ApiOperation(value = "划转可选项",
            notes = "from为所有可以划转的起始点，from对应的to为每个from可划转的终点\n" +
                    "账号中的balance字段为可划转余额\n " +
                    "账户类型 QUANT:资产账户(USDT) MINING:资产账户(FIL) PLATFORM:平台账户(BTE) QUANT_COMMUNITY:量化社区  QUANT_SERVICE:量化服务费 MINING_COMMUNITY:矿机社区 MINING_PROFIT:矿机收益 MINING_PROFIT:矿机收益 PLATFORM_SHARE:平台分红"
    )
    @GetMapping("/transfer-options/{symbol}")
    public CommonResult<List<TransferOptionVO>> transferOptions(@PathVariable("symbol") String symbol) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        List<TransferOptionVO> transferOptionVOS = walletService.transferOptions(currentUserId, symbol);
        return CommonResult.success(transferOptionVOS);
    }

    @ApiOperation(value = "划转",
            notes = "from 和 to 对应可选项中type\n")
    @PostMapping("/transfer")
    public CommonResult transfer(@RequestBody TransferParam param) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        walletService.transfer(currentUserId, param.getFrom(), param.getTo(), param.getAmount());
        return CommonResult.success();
    }

    @ApiOperation(value = "划转明细")
    @GetMapping
    public CommonResult<Page<UcTransfer>> list(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Page<UcTransfer> list = transferService.list(currentUserId, page, size);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "锁仓服务费")
    @PostMapping("/freeze/service")
    public CommonResult freezeService(@RequestBody BigDecimal amount) {
        Long memberId = SecurityUtils.getCurrentUserId();
        walletService.freezeService(memberId, amount);
        return CommonResult.success();
    }
}

