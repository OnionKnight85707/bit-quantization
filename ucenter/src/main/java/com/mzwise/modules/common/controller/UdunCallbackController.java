package com.mzwise.modules.common.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.bte.bipay.constant.UdunCallbackStatusEnum;
import com.bte.bipay.constant.UdunTradeTypeEnum;
import com.bte.bipay.http.client.BiPayClient2;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.modules.common.dto.UdunCallbackBody;
import com.mzwise.modules.ucenter.service.BiPayService;
import com.mzwise.modules.ucenter.service.UcRechargeService;
import com.mzwise.modules.ucenter.service.UcWithdrawService;
import com.uduncloud.sdk.util.UdunUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;

@RestController
@ApiIgnore
@Api(tags = "优盾回调地址(切勿调用)")
@RequestMapping(value = "/bipay")
public class UdunCallbackController {

    private Logger logger = LoggerFactory.getLogger(UdunCallbackController.class);

    @Value("${bipay.merchantId}")
    private Long merchantId;
    @Value("${bipay.merchantKey}")
    private String merchantKey;

    @Autowired
    private BiPayClient2 biPayClient;

    @Autowired
    private BiPayService biPayService;

    @Autowired
    private UcRechargeService rechargeService;

    @Autowired
    private UcWithdrawService withdrawService;

    /**
     * 优盾充币/提币回调
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param body 回调实体
     * @param sign 签名
     * @return
     */
    @AnonymousAccess
    @PostMapping("/notify")
    public synchronized String tradeCallback(@RequestParam("timestamp") String timestamp,
                                             @RequestParam("nonce") String nonce,
                                             @RequestParam("body") String body,
                                             @RequestParam("sign") String sign) throws Exception {
        logger.info("钱包回调 body:{}", JSON.toJSONString(body));
        if ( ! UdunUtils.checkSign(merchantKey, timestamp, nonce, body, sign)) {
            logger.error("签名错误");
            return "error";
        }
        UdunCallbackBody udunCallbackBody = JSONUtil.toBean(body, UdunCallbackBody.class);
        if (udunCallbackBody.getTradeType() == UdunTradeTypeEnum.DEPOSIT_CALLBACK.getCode()) {
            logger.info("充币回调处理");
            if (udunCallbackBody.getStatus() != UdunCallbackStatusEnum.TRANSACTION_SUCCESSFUL.getCode()) {
                logger.info("地址={}，充币回调状态为={}", udunCallbackBody.getAddress(), UdunCallbackStatusEnum.findByCode(udunCallbackBody.getStatus()).getDesc());
                return "success";
            }
            Boolean result = rechargeService.success(null, udunCallbackBody.getAddress(), udunCallbackBody.getAmount(),
                    udunCallbackBody.getFee(), udunCallbackBody.getDecimals(), udunCallbackBody.getTxId());
            if (result) {
                return "success";
            } else {
                return "fail";
            }
        } else if (udunCallbackBody.getTradeType() == UdunTradeTypeEnum.WITHDRAWAL_CALLBACK.getCode()) {
            logger.info("提币回调处理");
        }
        return "success";
    }

    public static void main(String[] args) {
        double pow = Math.pow(10, 6);
        System.out.println(pow);
        double result = 1200000 / pow;
        System.out.println(result);
        BigDecimal bd = new BigDecimal(result).setScale(8, BigDecimal.ROUND_HALF_UP);
        System.out.println(bd);
    }

}
