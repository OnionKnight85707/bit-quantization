package com.mzwise.modules.ucenter.controller;


import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.ucenter.dto.UcRealNameSubmitApplyParam;
import com.mzwise.modules.ucenter.entity.UcRealNameVerified;
import com.mzwise.modules.ucenter.service.UcRealNameVerifiedService;
import com.mzwise.common.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Api(tags = "实名认证")
@RestController
@RequestMapping("/ucenter/ucRealNameVerified")
public class UcRealNameVerifiedController {
    @Autowired
    private UcRealNameVerifiedService ucRealNameVerifiedService;

    @PostMapping("/submitApply")
    @ApiOperation(value = "提交实名认证")
    public CommonResult submitApply(@RequestBody UcRealNameSubmitApplyParam realNameSubmitApplyParam) {
        ucRealNameVerifiedService.submitApply(SecurityUtils.getCurrentUserId(), realNameSubmitApplyParam);
        return CommonResult.success();
    }

    @GetMapping("/queryLast")
    @ApiOperation("查询用户最后一条审核记录")
    public CommonResult<UcRealNameVerified> queryLastByMemberId() {
        Long memberId = SecurityUtils.getCurrentUserId();
        return CommonResult.success(ucRealNameVerifiedService.queryLastByMemberId(memberId));
    }

    @GetMapping("/getDetail")
    @ApiOperation("获取单个实名认证详情")
    public CommonResult<UcRealNameVerified> getDetail(Long realNameVerifiedId) {
        return CommonResult.success(ucRealNameVerifiedService.getById(realNameVerifiedId));
    }

}

