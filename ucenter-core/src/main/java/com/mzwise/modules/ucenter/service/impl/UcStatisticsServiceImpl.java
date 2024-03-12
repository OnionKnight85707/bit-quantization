package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.dto.UcStatisticsParam;
import com.mzwise.modules.ucenter.entity.UcStatistics;
import com.mzwise.modules.ucenter.mapper.UcStatisticsMapper;
import com.mzwise.modules.ucenter.service.UcStatisticsService;
import com.mzwise.modules.ucenter.vo.UcStatisticsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author 666
 * @since 2022-08-01
 */
@Slf4j
@Service
public class UcStatisticsServiceImpl extends ServiceImpl<UcStatisticsMapper, UcStatistics> implements UcStatisticsService {

    @Autowired
    private UcStatisticsMapper ucStatisticsMapper;

    /**
     *  新增每日数据统计
     */
    @Override
    public void resetEverydayDataStatistics() {
         baseMapper.resetEverydayDataStatistics();
    }

    /**
     * 增加注册用户数
     */
    @Override
    public void addRegisterNum() {
        try {
            int i = baseMapper.addRegisterNum();
            if (i==0){
                baseMapper.resetEverydayDataStatistics();
                log.error("统计错误：增加注册用户数(初始化当天统计数据)");
            }
        } catch (Exception e) {
            log.error("统计错误：增加注册用户数");
            e.printStackTrace();
        }
    }

    /**
     * 增加网络充币数量
     *
     * @param amount
     */
    @Override
    public void addRechargeOnline(BigDecimal amount) {
        try {
            int i = baseMapper.addRechargeOnline(amount);
            if (i==0){
                baseMapper.resetEverydayDataStatistics();
                log.error("统计错误：增加网络充币数量(初始化当天统计数据)");
            }
        } catch (Exception e) {
            log.error("统计错误：增加网络充币数量");
            e.printStackTrace();
        }
    }

    /**
     * 增加后台充币数量
     *
     * @param amount
     */
    @Override
    public void addRechargeBackstage(BigDecimal amount) {
        try {
            int i = baseMapper.addRechargeBackstage(amount);
            if (i==0){
                baseMapper.resetEverydayDataStatistics();
                log.error("统计错误：增加后台充币数量(初始化当天统计数据)");
            }
        } catch (Exception e) {
            log.error("统计错误：增加后台充币数量");
            e.printStackTrace();
        }
    }

    /**
     * 增加提币(成功)数量
     *
     * @param amount
     */
    @Override
    public void addWithdrawSuccess(BigDecimal amount) {
        try {
            int i = baseMapper.addWithdrawSuccess(amount);
            if (i==0){
                baseMapper.resetEverydayDataStatistics();
                log.error("统计错误：增加提币(成功)数量(初始化当天统计数据)");
            }
        } catch (Exception e) {
            log.error("统计错误：增加提币(成功)数量");
            e.printStackTrace();
        }
    }

    /**
     * 增加用户合约收益
     *
     * @param amount
     */
    @Override
    public void addUserSwapProfit(BigDecimal amount) {
        try {
            int i = baseMapper.addUserSwapProfit(amount);
            if (i==0){
                baseMapper.resetEverydayDataStatistics();
                log.error("统计错误：增加用户合约收益(初始化当天统计数据)");
            }
        } catch (Exception e) {
            log.error("统计错误：增加用户合约收益");
            e.printStackTrace();
        }
    }

    /**
     * 增加公司收益
     *
     * @param amount
     */
    @Override
    public void addCompanyProfit(BigDecimal amount) {
        try {
            int i = baseMapper.addCompanyProfit(amount);
            if (i==0){
                baseMapper.resetEverydayDataStatistics();
                log.error("统计错误：增加公司收益(初始化当天统计数据)");
            }
        } catch (Exception e) {
            log.error("统计错误：增加公司收益");
            e.printStackTrace();
        }
    }

    /**
     * 减少公司收益
     *
     * @param amount
     */
    @Override
    public void subtractCompanyProfit(BigDecimal amount) {
        try {
            int i = baseMapper.subtractCompanyProfit(amount);
            if (i == 0) {
                baseMapper.resetEverydayDataStatistics();
                log.error("统计错误：减少公司收益(初始化当天统计数据)");
            }
        } catch (Exception e) {
            log.error("统计错误：减少公司收益");
            e.printStackTrace();
        }
    }

    /**
     * 增加合伙人佣金
     *
     * @param amount
     */
    @Override
    public void addPartnerCommission(BigDecimal amount) {
        try {
            int i = baseMapper.addPartnerCommission(amount);
            if(i==0){
                baseMapper.resetEverydayDataStatistics();
                log.error("统计错误：增加合伙人佣金(初始化当天统计数据)");
            }
        } catch (Exception e) {
            log.error("统计错误：增加合伙人佣金");
            e.printStackTrace();
        }
    }

    /**
     * 分页查看统计数据
     * @param param
     * @return
     */
    @Override
    public Page<UcStatisticsVo> listAllStatistics(UcStatisticsParam param) {
        Page<UcStatisticsParam> page = new Page<>(param.getPageNum(), param.getPageSize());
        return ucStatisticsMapper.listAllStatistics(page, param.getBeginTime(), param.getEndTime());
    }
}
