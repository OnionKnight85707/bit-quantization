package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.DistributionProfitTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcBteDividendsRecord;
import com.mzwise.modules.ucenter.entity.UcDistributionProfit;
import com.mzwise.modules.ucenter.mapper.UcBteDividendsRecordMapper;
import com.mzwise.modules.ucenter.mapper.UcDistributionProfitMapper;
import com.mzwise.modules.ucenter.service.AdminDistributionProfitService;
import com.mzwise.modules.ucenter.vo.AdminBTEDividendRecordVO;
import com.mzwise.modules.ucenter.vo.AdminDistributionProfitRecordVO;
import net.sf.jsqlparser.statement.select.Wait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/21
 */
@Service
public class AdminDistributionProfitServiceImpl implements AdminDistributionProfitService {
    @Autowired
    private UcDistributionProfitMapper distributionProfitMapper;
    @Autowired
    private UcBteDividendsRecordMapper bteDividendsRecordMapper;

    @Override
    public Page<AdminDistributionProfitRecordVO> listSelective(String nickname, String phone, String email, DistributionProfitTypeEnum typeEnum, Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<AdminDistributionProfitRecordVO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcDistributionProfit> wrapper = new QueryWrapper<>();
        wrapper.ge("udp.id", 1);
        if (typeEnum != null) {
            wrapper.lambda().eq(UcDistributionProfit::getType, typeEnum);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(UcDistributionProfit::getCreateTime, beginDate, endDate);
        }
        return distributionProfitMapper.listProfitRecord(page, nickname, phone, email, wrapper);
    }

    @Override
    public Page<AdminBTEDividendRecordVO> listBteDividendRecord(String nickname, String phone, String email, Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<AdminBTEDividendRecordVO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcBteDividendsRecord> wrapper = new QueryWrapper<>();
        wrapper.ge("ubdr.id", 1);
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(UcBteDividendsRecord::getCreateTime, beginDate, endDate);
        }
        return bteDividendsRecordMapper.listSelective(page, nickname, phone, email, wrapper);
    }
}
