package com.mzwise.modules.ucenter.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.ucenter.dto.ExchangeParam;
import com.mzwise.modules.ucenter.entity.UcExchange;
import com.mzwise.modules.ucenter.service.UcExchangeService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import com.mzwise.modules.ucenter.vo.ExchangeOptionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
@Api(tags = "兑换")
@RestController
@RequestMapping("/ucenter/exchange")
public class UcExchangeController {

    @Autowired
    private UcWalletService walletService;

    @Autowired
    private UcExchangeService exchangeService;

    @ApiOperation(value = "兑换可选项",
            notes = "from为所有可以兑换的起始点，from对应的to为每个from可兑换的终点\n" +
                    "from中的balance字段为可兑换余额\n " +
                    "to中rate字段为汇率 如rate=10, 那么一个from可以兑换10个to\n " +
                    "to中fee字段为手续费率。手续费收取被兑换币种的手续费，如用一个from可以兑换10个to,手续费率为0.1, 实际应缴纳手续费10X0.1=1(单位为to的单位)\n " +
                    "账户类型 QUANT:资产账户(USDT) MINING:资产账户(FIL) PLATFORM:平台账户(BTE)"
    )
    @GetMapping("/exchange-options")
    public CommonResult<List<ExchangeOptionVO>> exchangeOptions() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        List<ExchangeOptionVO> exchangeOptionVOS = walletService.exchangeOptions(currentUserId);
        return CommonResult.success(exchangeOptionVOS);
    }

    @ApiOperation(value = "兑换",
            notes = "from 和 to 对应可选项中type")
    @PostMapping("/exchange")
    public CommonResult exchange(@RequestBody ExchangeParam param) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        walletService.exchange(currentUserId, param.getFrom(), param.getTo(), param.getAmount());
        return CommonResult.success();
    }

    @ApiOperation(value = "兑换明细")
    @GetMapping
    public CommonResult<Page<UcExchange>> list(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Page<UcExchange> list = exchangeService.list(currentUserId, page, size);
        return CommonResult.success(list);
    }
}

