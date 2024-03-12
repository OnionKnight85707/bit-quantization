package com.mzwise.modules.home.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.mzwise.modules.home.entity.HomeCoin;
import com.mzwise.modules.home.mapper.HomeCoinMapper;
import com.mzwise.modules.home.service.HomeCoinService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-30
 */
@Service
public class HomeCoinServiceImpl extends ServiceImpl<HomeCoinMapper, HomeCoin> implements HomeCoinService {

    @Override
    public List<HomeCoin> listCan(SFunction<HomeCoin, Boolean> column) {
        QueryWrapper<HomeCoin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(column, true);
        return list(wrapper);
    }

    @Override
    public HashMap<String, HomeCoin> listCanMap(SFunction<HomeCoin, Boolean> column) {
        List<HomeCoin> coins = listCan(column);
        HashMap<String, HomeCoin> re = new HashMap<>();
        for (HomeCoin coin : coins) {
            re.put(coin.getSymbol(), coin);
        }
        return re;
    }

    @Override
    public HomeCoin findByUdunCodeAndUdunSubCode(String udunCode, String udunSubCode) {
        QueryWrapper<HomeCoin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(HomeCoin::getUdunCode, udunCode).eq(HomeCoin::getUdunSubCode, udunSubCode);
        return getOne(wrapper);
    }
}
