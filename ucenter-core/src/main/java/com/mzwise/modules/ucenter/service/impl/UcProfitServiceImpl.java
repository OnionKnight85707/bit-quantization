package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.component.RatePool;
import com.mzwise.constant.UnitEnum;
import com.mzwise.modules.ucenter.entity.UcProfit;
import com.mzwise.modules.ucenter.entity.UcProfitDetail;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.modules.ucenter.mapper.UcProfitDetailMapper;
import com.mzwise.modules.ucenter.mapper.UcProfitMapper;
import com.mzwise.modules.ucenter.service.UcProfitDetailService;
import com.mzwise.modules.ucenter.service.UcProfitService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import com.mzwise.modules.ucenter.vo.ProfitWithSymbolVO;
import com.mzwise.modules.ucenter.vo.UserProfitGeneralVO;
import com.mzwise.modules.ucenter.vo.WalletAllTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-04-01
 */
@Service
public class UcProfitServiceImpl extends ServiceImpl<UcProfitMapper, UcProfit> implements UcProfitService {

    @Autowired
    private UcWalletService walletService;

    @Autowired
    private RatePool ratePool;

    @Override
    public UserProfitGeneralVO general(Long memberId) {
        UserProfitGeneralVO profit = new UserProfitGeneralVO();
//        WalletAllTypeVO wallets = walletService.getWalletsEntity(memberId);
//        // 量化收益
//        profit.setQuant(new ProfitWithSymbolVO(wallets.getQUANT_SERVICE().getTotalProfit(), wallets.getQUANT_SERVICE().getTodayProfit(), wallets.getQUANT_SERVICE().getSymbol()));
//        // 量化社区
//        profit.setQuantCommunity(new ProfitWithSymbolVO(wallets.getQUANT_COMMUNITY().getTotalProfit(), wallets.getQUANT_COMMUNITY().getTodayProfit(), wallets.getQUANT_COMMUNITY().getSymbol()));
//        // 矿机社区
//        profit.setMiningCommunity(new ProfitWithSymbolVO(wallets.getMINING_COMMUNITY().getTotalProfit(), wallets.getMINING_COMMUNITY().getTodayProfit(), wallets.getMINING_COMMUNITY().getSymbol()));
//        // 矿机收益
//        profit.setMiningProfit(new ProfitWithSymbolVO(wallets.getMINING_PROFIT().getTotalProfit(), wallets.getMINING_PROFIT().getTodayProfit(), wallets.getMINING_PROFIT().getSymbol()));
//        // 分红收益
//        profit.setPlatformShare(new ProfitWithSymbolVO(wallets.getPLATFORM_SHARE().getTotalProfit(), wallets.getPLATFORM_SHARE().getTodayProfit(), wallets.getPLATFORM_SHARE().getSymbol()));
//        // 总体收益
//        ProfitWithSymbolVO total = getTotal(new ArrayList<ProfitWithSymbolVO>() {{
//            add(profit.getQuant());
//            add(profit.getQuantCommunity());
//            add(profit.getMiningCommunity());
//            add(profit.getMiningProfit());
//            add(profit.getPlatformShare());
//        }});
//        profit.setTotal(total);
        UcWallet masterWallet = walletService.getMasterWallet(UnitEnum.USDT.getName(), memberId);
        profit.setQuant(new ProfitWithSymbolVO(masterWallet.getTotalProfit(), masterWallet.getTodayProfit(), masterWallet.getSymbol()));
        profit.setTotal(new ProfitWithSymbolVO(masterWallet.getTotalProfit(), masterWallet.getTodayProfit(), masterWallet.getSymbol()));
        return profit;
    }

    /**
     * 更新合约收益
     * @param memberId 用户id
     * @param profit 收益
     */
    @Override
    public void updateSwapProfit(Long memberId, BigDecimal profit) {
        baseMapper.updateSwapProfit(memberId, profit);
    }

    /**
     * 计算总收益，转换为USDT
     * @param items
     * @return
     */
    private ProfitWithSymbolVO getTotal(List<ProfitWithSymbolVO> items) {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal today = BigDecimal.ZERO;
        for (ProfitWithSymbolVO item : items) {
            total = total.add(item.getTotal().multiply(ratePool.getRate(item.getSymbol())));
            today = today.add(item.getToday().multiply(ratePool.getRate(item.getSymbol())));
        }
        return new ProfitWithSymbolVO(total, today, "USDT");
    }
}


