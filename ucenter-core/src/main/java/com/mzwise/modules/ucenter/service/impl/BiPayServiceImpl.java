package com.mzwise.modules.ucenter.service.impl;

import com.bte.bipay.entity.Transaction;
import com.bte.bipay.http.client.BiPayClient2;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.util.HttpServletUtil;
import com.mzwise.modules.home.entity.HomeCoin;
import com.mzwise.modules.home.service.HomeCoinService;
import com.mzwise.modules.ucenter.service.BiPayService;
import com.bte.bipay.entity.Trade;
import com.bte.bipay.http.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Service
public class BiPayServiceImpl implements BiPayService {
    @Autowired
    private BiPayClient2 biPayClient;
    @Autowired
    private HomeCoinService coinService;

    /**
     * 提币
     * @param orderId
     * @param amount
     * @param coin
     * @param address
     * @param memo
     * @return
     */
    @Override
    public String withdraw(HttpServletRequest request, String orderId, BigDecimal amount, HomeCoin coin, String address, String memo) {

        String callbackUrl = HttpServletUtil.getHttpUrl(request) + "/master/bipay/notify";


        /*ResponseMessage<Transaction> resp = null;
        try {
            resp = biPayClient.transfer(orderId, amount, coin.getUdunCode(), coin.getUdunSubCode(), address, callbackUrl, memo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resp.getCode()!= ResponseMessage.SUCCESS_CODE) {
            throw new ApiException(resp.getMessage());
        }
        return resp.getData().getTransaction();*/

        return "待测试";

    }

    /**
     * 创建币种地址
     * @param coin
     * @return
     */
    @Override
    public String createCoinAddress(HttpServletRequest request, HomeCoin coin) {
//        String callbackUrl = HttpServletUtil.getHttpUrl(request) + "/master/bipay/notify";
//        try {
//            ResponseMessage<Address> resp =  biPayClient.createCoinAddress(coin.getUdunCode(), callbackUrl, "", "");
//            return  resp.getData().getAddress();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
        return biPayClient.createCoinAddress(coin.getUdunCode(), null, null);
    }

    // 根据trade 查找Coin
    @Override
    public HomeCoin convert2Coin(Trade trade) {
        String mainCoinType = trade.getMainCoinType();
        String coinType = trade.getCoinType();
        return coinService.findByUdunCodeAndUdunSubCode(mainCoinType, coinType);
    }
}