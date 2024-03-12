package com.mzwise.modules.quant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.dto.IdAndNameVo;
import com.mzwise.modules.quant.service.QuantOuterSignalService;
import com.mzwise.modules.quant.vo.QuantOuterSignalRequest;
import com.mzwise.modules.quant.vo.QuantSmallStrategyRelaVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 智能选币控制器
 * @Author 666
 * @Date 2022/08/16
 */
@Api(tags = "外部信号记录")
@RestController
@RequestMapping("/quant/outerSignal")
public class AdminQuantOuterSignalController {

    @Autowired
    private QuantOuterSignalService quantOuterSignalService;

    @ApiOperation("查询外部信号触发的小类策略")
    @GetMapping("/query/smallStrategyName")
    public CommonResult querySmallStrategyBySignal() {
        List<IdAndNameVo> smallStrategyNameList = quantOuterSignalService.querySmallStrategyBySignal();
        return CommonResult.success(smallStrategyNameList);
    }

    @ApiOperation("分页查询外部信号日志")
    @PostMapping("/query/outerSignal")
    public CommonResult<Page<QuantSmallStrategyRelaVO>> queryQuantOuterSignal(@RequestBody QuantOuterSignalRequest request) {
        Page<QuantSmallStrategyRelaVO> page = quantOuterSignalService.queryQuantOuterSignal(request);
        return CommonResult.success(page);
    }

}
