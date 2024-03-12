package com.mzwise.modules.ucenter.service;

import com.mzwise.modules.home.entity.HomeCoin;
import com.bte.bipay.entity.Trade;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public interface BiPayService {
    /**
     * 提币
     * @param request
     * @param orderId
     * @param amount
     * @param coin
     * @param address
     * @param memo
     * @return
     */
     String withdraw(HttpServletRequest request, String orderId, BigDecimal amount, HomeCoin coin, String address, String memo);


    /**
     * 创建币种地址
     * @param request
     * @param coin
     * @return
     */
    String createCoinAddress(HttpServletRequest request, HomeCoin coin);

    /**
     * 根据trade 查找Coin
     * @param trade
     * @return
     */
    HomeCoin convert2Coin(Trade trade);
}