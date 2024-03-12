package com.mzwise.common.component;

import com.mzwise.common.exception.ApiException;
import com.mzwise.modules.home.entity.HomeCoinRate;
import com.mzwise.modules.home.mapper.HomeCoinRateMapper;
import com.mzwise.modules.home.service.HomeCoinRateService;
import com.mzwise.modules.ucenter.vo.AmountWithSymbolVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@CacheConfig(cacheNames = "bte.coin-rate")
public class RatePool {

    @Resource
    private HomeCoinRateService rateService;

    // 获取某币种汇率
    @Cacheable(key = "#coin")
    public BigDecimal getRate(String coin) {
        if (coin.equals("USDT") || coin.equals("USD")) {
            return BigDecimal.ONE;
        }
        HomeCoinRate s = rateService.getById(coin);
        if (s==null) {
            throw new ApiException("汇率计算出现严重错误");
        }
        return s.getRate();
    }

    /**
     * 计算总收益，转换为USDT
     * @param items
     * @return
     */
    public AmountWithSymbolVO getTotal(List<AmountWithSymbolVO> items) {
        BigDecimal totalUsd = BigDecimal.ZERO;
        for (AmountWithSymbolVO item : items) {
            totalUsd.add(item.getAmount().multiply(getRate(item.getSymbol())));
        }
        return new AmountWithSymbolVO(totalUsd, "USDT");
    }

    /**
     * 计算两个币种折合汇率
     * @param coin1
     * @param coin2
     * @return 一个coin1 等于 ? 个coin2
     */
    public BigDecimal getRate(String coin1, String coin2) {
        BigDecimal rate1 = getRate(coin1);
        BigDecimal rate2 = getRate(coin2);
        return rate1.divide(rate2, 6, RoundingMode.HALF_UP);
    }

    // 获取某币种汇率
    @Cacheable(key = "'USD-CNY'")
    public BigDecimal getUsdCnyRate() {
        HomeCoinRate coinRate = rateService.getById("USD-CNY");
        return coinRate.getRate();
    }

    /**
     * 只有一个项目进行跟新操作就够了
     * @param coin
     * @param price
     */
    @CacheEvict(key = "#coin")
    public void setRate(String coin, BigDecimal price) {
        HomeCoinRate coinRate = rateService.getById(coin);
        coinRate.setRate(price);
        rateService.updateById(coinRate);
    }
}
