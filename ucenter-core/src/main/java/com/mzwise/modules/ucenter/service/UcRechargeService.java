package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.RechargeTypeEnum;
import com.mzwise.modules.home.entity.HomeCoin;
import com.mzwise.modules.ucenter.entity.UcRecharge;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.entity.UcWallet;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
public interface UcRechargeService extends IService<UcRecharge> {

    /**
     * 分页展示充币明细
     * @param memberId
     * @param page
     * @param size
     * @return
     */
    Page<UcRecharge> list(Long memberId, Integer page, Integer size);

    /**
     * 通过address和txid查询充币记录
     * @param address
     * @param txid
     * @return
     */
    UcRecharge getByAddressAndTxid(String address, String txid);

    /**
     * 获取充币可选项和地址等
     * @param currentUserId
     * @return
     */
    List<UcWallet> getOptions(Long currentUserId);

    /**
     * 根据某个用户某个币种创建充币地址
     * @param currentUserId
     * @param request
     * @param symbol
     * @return
     */
    String createCoinAddress(Long currentUserId, HttpServletRequest request, String symbol);

    /**
     * 保存充值记录
     * @param wallet 钱包
     * @param encodeAddress 加密后地钱包址
     * @param amount 充值金额
     * @param txId 链上交易id
     * @param rechargeType 充值类型
     * @return
     */
    boolean saveRecharge(UcWallet wallet, String encodeAddress, BigDecimal amount, String txId, RechargeTypeEnum rechargeType);

    /**
     * 充币成功
     * @param coin 币种
     * @param address 地址
     * @param amount 交易數量
     * @param fee 矿工费
     * @param decimals 币种精度
     * @param txId 区块链交易哈希
     * @return
     */
    Boolean success(HomeCoin coin, String address, String amount, String fee, String decimals, String txId) throws Exception;

}
