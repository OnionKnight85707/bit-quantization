package com.mzwise.modules.ucenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.DistributionProfitTypeEnum;
import com.mzwise.modules.distribution.service.DistributionService;
import com.mzwise.modules.ucenter.service.AdminDistributionProfitService;
import com.mzwise.modules.ucenter.vo.AdminBTEDividendRecordVO;
import com.mzwise.modules.ucenter.vo.AdminDistributionProfitRecordVO;
import com.mzwise.modules.ucenter.vo.SimpleMemberTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/05/20
 */
@Api(tags = "后台分销记录")
@RestController
@RequestMapping("/admin/distribution")
public class AdminDistributionController {
    @Autowired
    private DistributionService distributionService;
    @Autowired
    private AdminDistributionProfitService distributionProfitService;

    @ApiOperation("获取树结构")
    @GetMapping("/getTree")
    @AnonymousAccess
    public CommonResult<SimpleMemberTreeVO> queryByMemberId(@RequestParam Long memberId) {
        Long topPid = distributionService.getTopPid(memberId);
        SimpleMemberTreeVO simpleMemberTreeVO = distributionService.queryTreeByMemberId(topPid);
        return CommonResult.success(simpleMemberTreeVO);
    }

    @ApiOperation("分页条件查询量化分销记录")
    @GetMapping("/list")
    public CommonResult<Page<AdminDistributionProfitRecordVO>> listSelective(@RequestParam(required = false) @ApiParam(value = "昵称") String nickname,
                                                                             @RequestParam(required = false) @ApiParam(value = "手机号") String phone,
                                                                             @RequestParam(required = false) @ApiParam(value = "邮箱") String email,
                                                                             @RequestParam(required = false) @ApiParam(value = "收益类型") DistributionProfitTypeEnum typeEnum,
                                                                             @RequestParam(required = false) @ApiParam(value = "起始时间") Date beginDate,
                                                                             @RequestParam(required = false) @ApiParam(value = "结束时间") Date endDate,
                                                                             @RequestParam(defaultValue = "1") @ApiParam(value = "当前页") Integer pageNum,
                                                                             @RequestParam(defaultValue = "10") @ApiParam(value = "每页限制数量") Integer pageSize) {
        return CommonResult.success(distributionProfitService.listSelective(nickname, phone, email, typeEnum, beginDate, endDate, pageNum, pageSize));
    }

    @ApiOperation("分页条件查询平台币分红记录")
    @GetMapping("/bteDividend-list")
    public CommonResult<Page<AdminBTEDividendRecordVO>> listBTEDividendRecordSelective(@RequestParam(required = false) @ApiParam(value = "昵称") String nickname,
                                                                             @RequestParam(required = false) @ApiParam(value = "手机号") String phone,
                                                                             @RequestParam(required = false) @ApiParam(value = "邮箱") String email,
                                                                             @RequestParam(required = false) @ApiParam(value = "起始时间") Date beginDate,
                                                                             @RequestParam(required = false) @ApiParam(value = "结束时间") Date endDate,
                                                                             @RequestParam(defaultValue = "1") @ApiParam(value = "当前页") Integer pageNum,
                                                                             @RequestParam(defaultValue = "10") @ApiParam(value = "每页限制数量") Integer pageSize) {
        return CommonResult.success(distributionProfitService.listBteDividendRecord(nickname, phone, email, beginDate, endDate, pageNum, pageSize));
    }
}
