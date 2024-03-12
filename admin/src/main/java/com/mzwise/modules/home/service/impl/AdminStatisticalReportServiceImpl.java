package com.mzwise.modules.home.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonPage;
import com.mzwise.common.util.DateUtil;
import com.mzwise.constant.DistributionProfitTypeEnum;
import com.mzwise.modules.home.service.AdminStatisticalReportService;
import com.mzwise.modules.quant.mapper.QuantOrderMapper;
import com.mzwise.modules.quant.vo.AdminDayAndDuringDayVO;
import com.mzwise.modules.quant.vo.AdminQuantOrderProfitGroupByCoinVO;
import com.mzwise.modules.quant.vo.AdminQuantProfitGroupByCoinAndPlatformVO;
import com.mzwise.modules.quant.vo.AdminStatQuantOrderEachDayVO;
import com.mzwise.modules.ucenter.mapper.UcDistributionProfitMapper;
import com.mzwise.modules.ucenter.mapper.UcRechargeMapper;
import com.mzwise.modules.ucenter.vo.AdminStatDistributionGroupByDayAndTypeVO;
import com.mzwise.modules.ucenter.vo.AdminStatDistributionProfitEachDayVO;
import com.mzwise.modules.ucenter.vo.AdminStatRechargeAndWithdrawalEachDayVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author piao
 * @Date 2021/06/09
 */
@Service
public class AdminStatisticalReportServiceImpl implements AdminStatisticalReportService {
    @Autowired
    private QuantOrderMapper quantOrderMapper;
    @Autowired
    private UcRechargeMapper rechargeMapper;
    @Autowired
    private UcDistributionProfitMapper distributionProfitMapper;


    @Override
    public List<AdminQuantOrderProfitGroupByCoinVO> statQuantProfitGroupByCoin() {
        return quantOrderMapper.statOrderProfitGroupByCoin();
    }

    @Override
    public List<AdminQuantProfitGroupByCoinAndPlatformVO> statQuantProfitGroupByCoinAndPlatform() {
        return quantOrderMapper.statOrderProfitGroupByCoinAndPlatform();
    }

    @Override
    public Page<AdminStatRechargeAndWithdrawalEachDayVO> statRechargeAndWithdrawalEachDay(Date beginDate, Date endDate, Boolean showRecharge, Boolean showWithdrawal, Integer pageNum, Integer pageSize) {
        Page<AdminStatRechargeAndWithdrawalEachDayVO> page = new Page<>(pageNum, pageSize);
        AdminDayAndDuringDayVO dayAnDuringDay = getDayAnDuringDay(beginDate, endDate);
        return rechargeMapper.statRechargeAndWithdrawalEachDay(page, dayAnDuringDay.getBeginDate(), dayAnDuringDay.getEndDate(), dayAnDuringDay.getDuringDays(), showRecharge, showWithdrawal);
    }

    @Override
    public Page<AdminStatQuantOrderEachDayVO> statQuantOrderEachDay(Date beginDate,
                                                                    Date endDate,
                                                                    Boolean showProfit,
                                                                    Integer pageNum,
                                                                    Integer pageSize) {
        Page<AdminStatQuantOrderEachDayVO> page = new Page<>(pageNum, pageSize);
        AdminDayAndDuringDayVO dayAnDuringDay = getDayAnDuringDay(beginDate, endDate);
        return quantOrderMapper.statQuantOrderEachDay(page, dayAnDuringDay.getBeginDate(), dayAnDuringDay.getEndDate(), dayAnDuringDay.getDuringDays(), showProfit);
    }

    @Override
    @Deprecated
    public Page<AdminStatDistributionProfitEachDayVO> statDistributionProfitByTypeEachDay(Date beginDate,
                                                                                          Date endDate,
                                                                                          Integer pageNum,
                                                                                          Integer pageSize) {
        Page<AdminStatDistributionGroupByDayAndTypeVO> page = new Page<>(pageNum, pageSize);
        AdminDayAndDuringDayVO dayAnDuringDay = getDayAnDuringDay(beginDate, endDate);
        Page<AdminStatDistributionGroupByDayAndTypeVO> shareProfitPage = distributionProfitMapper.statDistributionProfitGroupByTypeEachDay(page, dayAnDuringDay.getBeginDate(), dayAnDuringDay.getEndDate(), dayAnDuringDay.getDuringDays(), DistributionProfitTypeEnum.SHARE.getValue());
        Page<AdminStatDistributionGroupByDayAndTypeVO> communityProfitPage = distributionProfitMapper.statDistributionProfitGroupByTypeEachDay(page, dayAnDuringDay.getBeginDate(), dayAnDuringDay.getEndDate(), dayAnDuringDay.getDuringDays(), DistributionProfitTypeEnum.COMMUNITY.getValue());
        Page<AdminStatDistributionGroupByDayAndTypeVO> dividendsProfitPage = distributionProfitMapper.statDistributionProfitGroupByTypeEachDay(page, dayAnDuringDay.getBeginDate(), dayAnDuringDay.getEndDate(), dayAnDuringDay.getDuringDays(), DistributionProfitTypeEnum.DIVIDENDS.getValue());

        List<AdminStatDistributionGroupByDayAndTypeVO> shareProfitList = shareProfitPage.getRecords();
        List<AdminStatDistributionGroupByDayAndTypeVO> communityProfitList = communityProfitPage.getRecords();
        List<AdminStatDistributionGroupByDayAndTypeVO> dividendsProfitList = dividendsProfitPage.getRecords();
        Page<AdminStatDistributionProfitEachDayVO> pageResult = new Page<>();
        List<AdminStatDistributionProfitEachDayVO> list = new ArrayList<>();
        for (int i = 0; i < shareProfitList.size(); i++) {
            AdminStatDistributionProfitEachDayVO profitEachDayVO = new AdminStatDistributionProfitEachDayVO();
            AdminStatDistributionGroupByDayAndTypeVO shareProfitVO = shareProfitList.get(i);
            AdminStatDistributionGroupByDayAndTypeVO communityProfitVO = communityProfitList.get(i);
            AdminStatDistributionGroupByDayAndTypeVO dividendsProfitVO = dividendsProfitList.get(i);

            profitEachDayVO.setDayDate(shareProfitVO.getDayDate());
            profitEachDayVO.setAmountOfShare(shareProfitVO.getAmount());
            profitEachDayVO.setAmountOfCommunity(communityProfitVO.getAmount());
            profitEachDayVO.setAmountOfDividend(dividendsProfitVO.getAmount());
            profitEachDayVO.setAmountOfTotal(shareProfitVO.getAmount().add(communityProfitVO.getAmount().add(dividendsProfitVO.getAmount())));
            list.add(profitEachDayVO);
        }
        pageResult.setCurrent(pageNum);
        pageResult.setSize(pageSize);
        pageResult.setTotal(shareProfitPage.getTotal());
        pageResult.setPages(shareProfitPage.getPages());
        pageResult.setRecords(list);
        return pageResult;
    }

    @Override
    public Page<AdminStatDistributionProfitEachDayVO> statDistributionProfitEachDay(Date beginDate,
                                                                                    Date endDate,
                                                                                    Boolean showShareProfit,
                                                                                    Boolean showCommunityProfit,
                                                                                    Boolean showDividendsProfit,
                                                                                    Boolean showTotalProfit,
                                                                                    Integer pageNum,
                                                                                    Integer pageSize) {
        Page<AdminStatDistributionProfitEachDayVO> page = new Page<>(pageNum, pageSize);
        AdminDayAndDuringDayVO dayAnDuringDay = getDayAnDuringDay(beginDate, endDate);
        return distributionProfitMapper.statDistributionProfitEachDay(page, dayAnDuringDay.getBeginDate(), dayAnDuringDay.getEndDate(), dayAnDuringDay.getDuringDays(), showShareProfit, showCommunityProfit, showDividendsProfit, showTotalProfit);
    }


    public AdminDayAndDuringDayVO getDayAnDuringDay(Date beginDate, Date endDate) {
        AdminDayAndDuringDayVO dayAndDuringDayVO = new AdminDayAndDuringDayVO();
        long diffDays;
        if (beginDate != null && endDate != null) {
            diffDays = DateUtil.diffDays(beginDate, endDate);
        } else {
            diffDays = 30L;
            endDate = new Date(System.currentTimeMillis());
            beginDate = DateUtil.dateAddDay(endDate, -30);
        }
        dayAndDuringDayVO.setBeginDate(beginDate);
        dayAndDuringDayVO.setEndDate(endDate);
        dayAndDuringDayVO.setDuringDays(diffDays);
        return dayAndDuringDayVO;
    }


}
