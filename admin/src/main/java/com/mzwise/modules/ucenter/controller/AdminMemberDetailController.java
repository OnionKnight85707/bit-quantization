package com.mzwise.modules.ucenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.MessageUtil;
import com.mzwise.constant.MessageChannelEnum;
import com.mzwise.modules.ucenter.dto.ManualDepositAndWithdrawalParam;
import com.mzwise.modules.ucenter.dto.SendMessageParam;
import com.mzwise.modules.ucenter.entity.UcRecharge;
import com.mzwise.modules.ucenter.entity.UcWithdraw;
import com.mzwise.modules.ucenter.service.*;
import com.mzwise.modules.ucenter.vo.AdminMemberAssetsVO;
import com.mzwise.modules.ucenter.vo.AdminMemberDepositAndWithdrawalVO;
import com.mzwise.modules.ucenter.vo.AdminMemberHomePageVO;
import com.mzwise.modules.ucenter.vo.AdminMemberProfitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author piao
 * @Date 2021/05/31
 */
@Api(tags = "后台会员详情")
@RestController
@RequestMapping("/admin/member/detail")
public class AdminMemberDetailController {
    @Autowired
    private AdminMemberDetailService memberDetailService;
    @Autowired
    private UcRechargeService rechargeService;
    @Autowired
    private UcWithdrawService withdrawService;
    @Autowired
    private UcMessageCenterService messageCenterService;

    @ApiOperation("展示会员详情——主页")
    @GetMapping("/homePage")
    public CommonResult<AdminMemberHomePageVO> showMemberHomePage(@RequestParam @ApiParam(required = true, value = "会员id") Long memberId) {
        return CommonResult.success(memberDetailService.showHomePage(memberId));
    }

    @ApiOperation("展示后台用户详情——资产")
    @GetMapping("/assets")
    public CommonResult<AdminMemberAssetsVO> showMemberAssets(@RequestParam @ApiParam(required = true, value = "会员id") Long memberId) {
        return CommonResult.success(memberDetailService.showMemberAssets(memberId));
    }


    @ApiOperation("展示后台用户详情——充提币")
    @GetMapping("/deposit-withdrawal")
    public CommonResult<AdminMemberDepositAndWithdrawalVO> showDepositAndWithdrawal(@RequestParam @ApiParam(required = true, value = "会员id") Long memberId) {
        return CommonResult.success(memberDetailService.showDepositAndWithdrawal(memberId));
    }

    @ApiOperation("展示后台用户详情——收益")
    @GetMapping("/profit")
    public CommonResult<AdminMemberProfitVO> showMemberProfitVO(@RequestParam @ApiParam(required = true, value = "会员id") Long memberId) {
        return CommonResult.success(memberDetailService.showMemberProfitVO(memberId));
    }

    @ApiOperation("展示后台用户详情——充币记录")
    @GetMapping("/recharge-record")
    public CommonResult<Page<UcRecharge>> showRechargeRecord(@RequestParam @ApiParam(required = true, value = "会员id") Long memberId,
                                                             @RequestParam(defaultValue = "1") @ApiParam("当前页") Integer pageNum,
                                                             @RequestParam(defaultValue = "10") @ApiParam("限制条数") Integer pageSize) {
        return CommonResult.success(rechargeService.list(memberId, pageNum, pageSize));
    }

    @ApiOperation("展示后台用户详情——提币记录")
    @GetMapping("/withdrawal-record")
    public CommonResult<Page<UcWithdraw>> showWithdrawalRecord(@RequestParam @ApiParam(required = true, value = "会员id") Long memberId,
                                                               @RequestParam(defaultValue = "1") @ApiParam("当前页") Integer pageNum,
                                                               @RequestParam(defaultValue = "10") @ApiParam("限制条数") Integer pageSize) {
        return CommonResult.success(withdrawService.list(memberId, pageNum, pageSize));
    }

    @ApiOperation("发送站内信")
    @PostMapping("/send-message")
    public CommonResult sendMessage(@RequestBody SendMessageParam param) {
        MessageUtil.send(param.getMemberId(), param.getTitle(), param.getContent());
        return CommonResult.success();
    }

    @ApiOperation("手动充值")
    @PostMapping("/manual-recharge")
    public CommonResult<Boolean> manualRecharge(@RequestBody ManualDepositAndWithdrawalParam param) {
        return CommonResult.success(memberDetailService.manualRecharge(param));
    }

    @ApiOperation("手动减币")
    @PostMapping("/manual-withdrawal")
    public CommonResult<Boolean> manualWithdrawal(@RequestBody ManualDepositAndWithdrawalParam param) {
        return CommonResult.success(memberDetailService.manualWithdrawal(param));
    }
}
