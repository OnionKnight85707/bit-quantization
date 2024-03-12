package com.mzwise.modules.home.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.admin.vo.DataStatisticalVO;
import com.mzwise.modules.home.service.AdminStatisticalReportService;
import com.mzwise.modules.quant.vo.AdminQuantOrderProfitGroupByCoinVO;
import com.mzwise.modules.quant.vo.AdminQuantProfitGroupByCoinAndPlatformVO;
import com.mzwise.modules.quant.vo.AdminStatQuantOrderEachDayVO;
import com.mzwise.modules.ucenter.service.DataAccumulationService;
import com.mzwise.modules.ucenter.vo.AdminStatDistributionProfitEachDayVO;
import com.mzwise.modules.ucenter.vo.AdminStatRechargeAndWithdrawalEachDayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * @Author piao
 * @Date 2021/06/09
 */
@Api(tags = "统计报表")
@RestController
@RequestMapping("/admin/statistical-report")
public class AdminStatisticalReportController {
    @Autowired
    private AdminStatisticalReportService adminStatisticalReportService;
    @Autowired
    private DataAccumulationService dataService;

    @ApiOperation("通过交易对统计量化收益")
    @GetMapping("/quantProfit/coin")
    public CommonResult<List<AdminQuantOrderProfitGroupByCoinVO>> statQuantProfitGroupByCoin() {
        return CommonResult.success(adminStatisticalReportService.statQuantProfitGroupByCoin());
    }

    @ApiOperation("通过交易对和平台 统计量化收益")
    @GetMapping("/quantProfit/coin-platform")
    public CommonResult<List<AdminQuantProfitGroupByCoinAndPlatformVO>> statQuantProfitGroupByCoinAndPlatform() {
        return CommonResult.success(adminStatisticalReportService.statQuantProfitGroupByCoinAndPlatform());
    }


    @ApiOperation("统计每日出入金")
    @GetMapping("/stat/recharge-withdrawal/each-day")
    public CommonResult<Page<AdminStatRechargeAndWithdrawalEachDayVO>> statRechargeAndWithdrawalEachDay(@ApiParam("起始时间") Date beginDate,
                                                                                                        @ApiParam("结束时间") Date endDate,
                                                                                                        @ApiParam("展示有入金") Boolean showRecharge,
                                                                                                        @ApiParam("展示有出金") Boolean showWithdrawal,
                                                                                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                                                                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        return CommonResult.success(adminStatisticalReportService.statRechargeAndWithdrawalEachDay(beginDate, endDate, showRecharge, showWithdrawal, pageNum, pageSize));
    }

    @ApiOperation("统计每日订单收益")
    @GetMapping("/stat/quant-order/each-day")
    public CommonResult<Page<AdminStatQuantOrderEachDayVO>> statQuantOrderEachDay(@ApiParam("起始时间") Date beginDate,
                                                                                  @ApiParam("结束时间") Date endDate,
                                                                                  @ApiParam("展示有收益") Boolean showProfit,
                                                                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        return CommonResult.success(adminStatisticalReportService.statQuantOrderEachDay(beginDate, endDate, showProfit, pageNum, pageSize));
    }

    @ApiOperation("统计每日量化分销收益")
    @GetMapping("/stat/distribution-profit/each-day")
    public CommonResult<Page<AdminStatDistributionProfitEachDayVO>> statDistributionProfitEachDay(@ApiParam("起始时间") Date beginDate,
                                                                                                  @ApiParam("结束时间") Date endDate,
                                                                                                  @ApiParam("展示有分享收益的") Boolean showShareProfit,
                                                                                                  @ApiParam("展示有社区奖收益的") Boolean showCommunityProfit,
                                                                                                  @ApiParam("展示有分红收益的") Boolean showDividendsProfit,
                                                                                                  @ApiParam("展示有收益的") Boolean showTotalProfit,
                                                                                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                                                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        return CommonResult.success(adminStatisticalReportService.statDistributionProfitEachDay(beginDate, endDate, showShareProfit, showCommunityProfit, showDividendsProfit, showTotalProfit, pageNum, pageSize));
    }

    @ApiOperation("统计看板")
    @GetMapping("/stat/bulletinBoard")
    public CommonResult<DataStatisticalVO> bulletinBoard(){
        DataStatisticalVO vo =new DataStatisticalVO();
        BigDecimal orderProfitTotal = dataService.orderProfitTotal();
        BigDecimal networkRechargeTotal = dataService.networkRechargeTotal();
        BigDecimal managementRechargeTotal = dataService.managementRechargeTotal();
        Long openStrategyUsersTotal = dataService.openStrategyUsersTotal();
        Long registeredTotal = dataService.registeredTotal();
        BigDecimal allBalance = dataService.getAllBalance();
        BigDecimal allGivenUSDT = dataService.getAllGivenUSDT();
        BigDecimal allUserBalance = dataService.getAllUserBalance();
        BigDecimal allWithdrawalAmount = dataService.getAllWithdrawalAmount();
        BigDecimal allPartnerTotalCommission = dataService.getAllPartnerTotalCommission();
        BigDecimal allTotalTicket = dataService.getAllTotalTicket();
        vo.setRegisteredTotal(registeredTotal);
        vo.setNetworkRechargeTotal(networkRechargeTotal);
        vo.setCompanyEarningsTotal(allBalance);
        vo.setFreeUSDTTotal(allGivenUSDT);
        vo.setManagementRechargeTotal(managementRechargeTotal);
        vo.setWithdrawTotal(allWithdrawalAmount);
        vo.setOpenStrategyUsersTotal(openStrategyUsersTotal);
        vo.setOrderProfitTotal(orderProfitTotal);
        vo.setUserWalletBalanceTotal(allUserBalance);
        vo.setPartnerCommissionTotal(allPartnerTotalCommission);
        vo.setUserWalletTicketTotal(allTotalTicket);
        return CommonResult.success(vo);
    }
}
