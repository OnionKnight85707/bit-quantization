package com.mzwise.modules.ucenter.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.constant.*;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.service.UmsAdminLogService;
import com.mzwise.modules.ucenter.dto.ManualActionBalanceParam;
import com.mzwise.modules.ucenter.entity.*;
import com.mzwise.modules.ucenter.service.AdminAssetService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import com.mzwise.modules.ucenter.vo.AdminAssetsRecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/01
 */
@Api(tags = "后台资产管理")
@RestController
@RequestMapping("/admin/assets")
public class AdminAssetsController {

    @Autowired
    private AdminAssetService adminAssetService;
    @Autowired
    private UcWalletService walletService;
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UmsAdminLogService adminLogService;

    @ApiOperation("后台查询充币记录")
    @GetMapping("/recharge-record")
    public CommonResult<Page<UcRecharge>> listRechargeRecordSelective(@RequestParam(required = false) @ApiParam(value = "昵称") String nickname,
                                                                      @RequestParam(required = false) @ApiParam(value = "手机号") String phone,
                                                                      @RequestParam(required = false) @ApiParam(value = "邮箱") String email,
                                                                      @RequestParam(required = false) @ApiParam(value = "币种") String symbol,
                                                                      @RequestParam(required = false) @ApiParam(value = "充值类型") RechargeTypeEnum rechargeType,
                                                                      @RequestParam(required = false) @ApiParam(value = "起始时间") Date beginDate,
                                                                      @RequestParam(required = false) @ApiParam(value = "结束时间") Date endDate,
                                                                      @RequestParam(defaultValue = "1") @ApiParam(value = "当前页") Integer pageNum,
                                                                      @RequestParam(defaultValue = "10") @ApiParam(value = "每页限制数量") Integer pageSize) {
        return CommonResult.success(adminAssetService.listRechargeRecordSelective(nickname, phone, email, symbol, rechargeType, beginDate, endDate, pageNum, pageSize));
    }

    @ApiOperation("后台查询 提币记录/提币审核列表")
    @GetMapping("/withdrawal-record")
    public CommonResult<Page<UcWithdraw>> listWithdrawalRecordSelective(@RequestParam(required = false) @ApiParam(value = "昵称") String nickname,
                                                                        @RequestParam(required = false) @ApiParam(value = "手机号") String phone,
                                                                        @RequestParam(required = false) @ApiParam(value = "邮箱") String email,
                                                                        @RequestParam(required = false) @ApiParam(value = "币种") String symbol,
                                                                        @RequestParam(required = false) @ApiParam(value = "起始时间") Date beginDate,
                                                                        @RequestParam(required = false) @ApiParam(value = "结束时间") Date endDate,
                                                                        @RequestParam @ApiParam(value = "是否是审核列表", required = true) Boolean isCheck,
                                                                        @RequestParam(defaultValue = "1") @ApiParam(value = "当前页") Integer pageNum,
                                                                        @RequestParam(defaultValue = "10") @ApiParam(value = "每页限制数量") Integer pageSize) {
        return CommonResult.success(adminAssetService.listWithdrawalRecordSelective(nickname, phone, email, symbol, beginDate, endDate, isCheck, pageNum, pageSize));
    }

    @ApiOperation("后台查询划转记录")
    @GetMapping("/transfer-record")
    public CommonResult<Page<UcTransfer>> listTransferRecordSelective(@RequestParam(required = false) @ApiParam(value = "昵称") String nickname,
                                                                      @RequestParam(required = false) @ApiParam(value = "手机号") String phone,
                                                                      @RequestParam(required = false) @ApiParam(value = "邮箱") String email,
                                                                      @RequestParam(required = false) @ApiParam(value = "币种") String symbol,
                                                                      @RequestParam(required = false) @ApiParam(value = "起始时间") Date beginDate,
                                                                      @RequestParam(required = false) @ApiParam(value = "结束时间") Date endDate,
                                                                      @RequestParam(defaultValue = "1") @ApiParam(value = "当前页") Integer pageNum,
                                                                      @RequestParam(defaultValue = "10") @ApiParam(value = "每页限制数量") Integer pageSize) {
        return CommonResult.success(adminAssetService.listTransferRecordSelective(nickname, phone, email, symbol, beginDate, endDate, pageNum, pageSize));
    }

    @ApiOperation("后台查询兑换记录")
    @GetMapping("/exchange-record")
    public CommonResult<Page<UcExchange>> listExchangeRecordSelective(@RequestParam(required = false) @ApiParam(value = "昵称") String nickname,
                                                                      @RequestParam(required = false) @ApiParam(value = "手机号") String phone,
                                                                      @RequestParam(required = false) @ApiParam(value = "邮箱") String email,
                                                                      @RequestParam(required = false) @ApiParam(value = "来源币种") String fromSymbol,
                                                                      @RequestParam(required = false) @ApiParam(value = "去向币种") String toSymbol,
                                                                      @RequestParam(required = false) @ApiParam(value = "起始时间") Date beginDate,
                                                                      @RequestParam(required = false) @ApiParam(value = "结束时间") Date endDate,
                                                                      @RequestParam(defaultValue = "1") @ApiParam(value = "当前页") Integer pageNum,
                                                                      @RequestParam(defaultValue = "10") @ApiParam(value = "每页限制数量") Integer pageSize) {
        return CommonResult.success(adminAssetService.listExchangeRecordSelective(nickname, phone, email, fromSymbol, toSymbol, beginDate, endDate, pageNum, pageSize));
    }

    @ApiOperation("后台查询资金记录")
    @GetMapping("/assets-record")
    public CommonResult<Page<AdminAssetsRecordVO>> listAssetsRecordSelective(@RequestParam(required = false) @ApiParam(value = "昵称") String nickname,
                                                                             @RequestParam(required = false) @ApiParam(value = "手机号") String phone,
                                                                             @RequestParam(required = false) @ApiParam(value = "邮箱") String email,
                                                                             @RequestParam(required = false) @ApiParam(value = "钱包类型") WalletTypeEnum walletType,
                                                                             @RequestParam(required = false) @ApiParam(value = "交易类型") TransactionTypeEnum transactionType,
                                                                             @RequestParam(required = false) @ApiParam(value = "起始时间") Date beginDate,
                                                                             @RequestParam(required = false) @ApiParam(value = "结束时间") Date endDate,
                                                                             @RequestParam(defaultValue = "1") @ApiParam(value = "当前页") Integer pageNum,
                                                                             @RequestParam(defaultValue = "10") @ApiParam(value = "每页限制数量") Integer pageSize) {
        return CommonResult.success(adminAssetService.listAdminAssetsRecord(nickname, phone, email, transactionType, walletType, beginDate, endDate, pageNum, pageSize));
    }

    @ApiOperation("后台审核出金")
    @GetMapping("/withdraw-check")
    public CommonResult checkWithdraw(HttpServletRequest request, @RequestParam @ApiParam(value = "出金id", required = true) Long id,
                                      @RequestParam @ApiParam(value = "是否通过", required = true) Boolean isPass,
                                      @ApiParam(value = "拒绝理由") String refuseReason) throws MessagingException {
        Long adminId = SecurityUtils.getCurrentAdminId();
        adminAssetService.checkWithdraw(request, id, isPass, refuseReason, adminId);
        return CommonResult.success();
    }

    @ApiOperation("手动增加余额")
    @PostMapping("/manualAddBalance")
    public CommonResult manualAddBalance(@RequestBody @Validated ManualActionBalanceParam param) {
        walletService.manualAddBalance(param.getMemberId(), param.getAmount());
        // 记录日志
        UcWallet masterWallet = walletService.getMasterWallet(UnitEnum.USDT.getName(), param.getMemberId());
        ManualActionBalanceParam manualActionBalanceParam = new ManualActionBalanceParam();
        BeanUtils.copyProperties(masterWallet, manualActionBalanceParam);
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.ADD.getValue(), AdminLogModuleEnum.ADD_BALANCE.getValue(),
                JSON.toJSONString(manualActionBalanceParam), JSON.toJSONString(param), "充币"));
        return CommonResult.success();
    }

    @ApiOperation("手动减少余额")
    @PostMapping("/manualSubtractBalance")
    public CommonResult manualSubtractBalance(@RequestBody @Validated ManualActionBalanceParam param) {
        walletService.manualSubtractBalance(param.getMemberId(), param.getAmount());
        // 记录日志
        UcWallet masterWallet = walletService.getMasterWallet(UnitEnum.USDT.getName(), param.getMemberId());
        ManualActionBalanceParam manualActionBalanceParam = new ManualActionBalanceParam();
        BeanUtils.copyProperties(masterWallet, manualActionBalanceParam);
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.DELETE.getValue(), AdminLogModuleEnum.SUBTRACT_BALANCE.getValue(),
                JSON.toJSONString(manualActionBalanceParam), JSON.toJSONString(param), "减币"));
        return CommonResult.success();
    }

}
