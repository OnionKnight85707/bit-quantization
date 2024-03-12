package com.mzwise.modules.ucenter.controller;


import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.quant.entity.QuantCoin;
import com.mzwise.modules.quant.service.QuantCoinService;
import com.mzwise.modules.ucenter.dto.BigStrategyParam;
import com.mzwise.modules.ucenter.dto.UniSmallStrategyParam;
import com.mzwise.modules.ucenter.entity.UniBigStrategy;
import com.mzwise.modules.ucenter.entity.UniSmallStrategy;
import com.mzwise.modules.ucenter.service.UniBigStrategyService;
import com.mzwise.modules.ucenter.service.UniSmallStrategyService;
import com.mzwise.modules.ucenter.vo.BigStrategyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "大小策略功能")
@RestController
@RequestMapping("/admin/BigAndSmallStrategy")
public class AdminStrategyController {
    @Autowired
    private UniBigStrategyService bigStrategyService;
    @Autowired
    private UniSmallStrategyService smallStrategyService;

    @Autowired
    private QuantCoinService quantCoinService;

    @ApiOperation("大小策略列表")
    @GetMapping("/list")
    public CommonResult<List<BigStrategyVO>> list(){
        List<BigStrategyVO> bigStrategyVO = bigStrategyService.queryUniBigStrategy();
        return CommonResult.success(bigStrategyVO);
    }

    @ApiOperation("新增大策略")
    @PostMapping("/addBigStrategy")
    public CommonResult<UniBigStrategy>  addBigStrategy(@RequestBody BigStrategyParam param){
        UniBigStrategy strategy = bigStrategyService.getByName(param.getName());
        Assert.isNull(strategy,"已存在该大策略");
        UniBigStrategy uniBigStrategy = bigStrategyService.addUniBigStrategy(param);
        return CommonResult.success(uniBigStrategy);
    }

    @ApiOperation("修改大策略")
    @PostMapping("/updateBigStrategy")
    public CommonResult updateBigStrategy(@RequestBody UniBigStrategy uniBigStrategy){
        UniBigStrategy strategy = bigStrategyService.getById(uniBigStrategy.getId());
        Assert.notNull(strategy,"该大策略不存在");
        bigStrategyService.updateUniBigStrategy(uniBigStrategy);
        return CommonResult.success();
    }

    /**
     * 1.新增/修改小策略，模板id一定得带上
     * 2.新增/修改小策略，如果模板开平方式 为1 （按照信号），生成token
     * 3.修改小策略，如果模板开平方式 不等于 1，token置为null
     * @param param
     * @return
     */
    @ApiOperation("新增小策略")
    @PostMapping("/addSmallStrategy")
    public CommonResult addSmallStrategy(@RequestBody UniSmallStrategyParam param){
        UniSmallStrategy smallStrategy = smallStrategyService.getByName(param.getName());
        Assert.isNull(smallStrategy,"已存在该小策略");
        UniSmallStrategy uniSmallStrategy = smallStrategyService.addUniSmallStrategy(param);
        return CommonResult.success(uniSmallStrategy);
    }

    @ApiOperation("修改小策略")
    @PostMapping("/updateSmallStrategy")
    public CommonResult updateSmallStrategy(@RequestBody UniSmallStrategy uniSmallStrategy){
        UniSmallStrategy strategy = smallStrategyService.getById(uniSmallStrategy.getId());
        Assert.notNull(strategy,"该小策略不存在");
        smallStrategyService.updateUniSmallStrategy(uniSmallStrategy);
        return CommonResult.success();
    }

    @ApiOperation("返回所有量化币种")
    @GetMapping("/coinList")
    public CommonResult<List<QuantCoin>> coinList(){
        List<QuantCoin> list = quantCoinService.getAllCoinList();
        return CommonResult.success(list);
    }

    @ApiOperation("单独返回大策略")
    @GetMapping("/singleList")
    public CommonResult<List<UniBigStrategy>> singleList(){
        List<UniBigStrategy> singlelist = bigStrategyService.singlelist();
        return CommonResult.success(singlelist);
    }

    @ApiOperation("小策略列表")
    @GetMapping("/smallStategyList")
    public CommonResult<List<UniSmallStrategy>> smallStategyList(){
        List<UniSmallStrategy> list = smallStrategyService.queryUniSmallStrategy();
        return CommonResult.success(list);
    }

    @ApiOperation("删除小策略")
    @PostMapping("/delSmallStrategy")
    public CommonResult delSmallStrategy(@RequestParam("id") Long id){
        smallStrategyService.delSmallStrategy(id);
        return CommonResult.success();
    }

    @ApiOperation("删除大策略")
    @PostMapping("/delBigStrategy")
    public CommonResult delBigStrategy(@RequestParam("id") Long id){
        bigStrategyService.delBigStrategy(id);
        return CommonResult.success();
    }
}
