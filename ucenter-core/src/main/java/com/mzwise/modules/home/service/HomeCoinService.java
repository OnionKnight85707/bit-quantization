package com.mzwise.modules.home.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.mzwise.modules.home.entity.HomeCoin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-30
 */
public interface HomeCoinService extends IService<HomeCoin> {
    /**
     * 获取可以充值的币种
     * @return
     */
    List<HomeCoin> listCan(SFunction<HomeCoin, Boolean> column);

    /**
     * 获取可以充值的币种
     * @return
     */
    HashMap<String, HomeCoin> listCanMap(SFunction<HomeCoin, Boolean> column);

    /**
     * 根据优盾编码获取币种
     * @param udunCode
     * @param udunSubCode
     * @return
     */
    HomeCoin findByUdunCodeAndUdunSubCode(String udunCode, String udunSubCode);


}
