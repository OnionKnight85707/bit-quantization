package com.mzwise.modules.ucenter.controller;

import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.ucenter.dto.SaveRelStrategyParam;
import com.mzwise.modules.ucenter.service.UniRelStrategyService;
import com.mzwise.modules.ucenter.vo.UserTypeStrategyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户类别策略控制器
 * @Author LiangZaiChao
 * @Date 2022/8/3 20:35
 */
@Api(tags = "用户类别策略控制器")
@RestController
@RequestMapping("/admin/relStrategy")
public class UniRelStrategyController {

    @Autowired
    private UniRelStrategyService relStrategyService;

    @ApiOperation(value = "用户类型和策略列表")
    @GetMapping("/userTypeStrategyList")
    public CommonResult<List<UserTypeStrategyVo>> userTypeStrategyList(@RequestParam Integer userTypeId) {
        List<UserTypeStrategyVo> list = relStrategyService.userTypeStrategyList(userTypeId);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "保存用户类型和策略关系")
    @PostMapping("/saveRelStrategy")
    public CommonResult saveRelStrategy(@RequestBody SaveRelStrategyParam param) {
        relStrategyService.saveRelStrategy(param);
        return CommonResult.success();
    }

    @ApiOperation("删除用户类型下的策略关系")
    @PostMapping("/delRelStrategy")
    public CommonResult delRelStrategy(@RequestParam Long userTypeId,@RequestParam Long smallStrategyId){
        relStrategyService.delRelStrategy(userTypeId,smallStrategyId);
        return CommonResult.success();
    }

}
