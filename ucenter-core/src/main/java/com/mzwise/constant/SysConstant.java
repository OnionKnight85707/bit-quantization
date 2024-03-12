package com.mzwise.constant;

import java.util.HashMap;

/**
 * 系统常量
 *
 * @author wmf
 * @date 2017年12月18日
 */
public class SysConstant {

    /**
     * 验证码
     */

    public static final String BIND_CODE_PREFIX = "BIND_CODE_";

    /**
     * 登录错误锁
     */
    public static final String LOGIN_LOCKED_PREFIX = "LOGIN_LOCKED_";

    /**
     * 系统设置reids
     */

    public static final String REDIS_KEY_SETTING = "SETTING";

    /**
     * 汇率
     */
    public static final String COIN_RATE_PREFIX = "COIN_RATE_";
    public static final HashMap<WalletTypeEnum, String> WALLET_TYPE_SYMBOL = new HashMap<WalletTypeEnum, String>() {{
//        put(WalletTypeEnum.QUANT, "USDT");
//        put(WalletTypeEnum.QUANT_COMMUNITY, "USDT");
        put(WalletTypeEnum.QUANT_SERVICE,"USDT");
//        put(WalletTypeEnum.MINING, "FIL");
//        put(WalletTypeEnum.MINING_COMMUNITY, "FIL");
//        put(WalletTypeEnum.MINING_PROFIT, "FIL");
//        put(WalletTypeEnum.PLATFORM, "BTE");
//        put(WalletTypeEnum.PLATFORM_SHARE, "BTE");
    }};

    public static final String SOCKET_USER_PREFIX = "SOCKET_USER_";

    public static final String SOCKET_REMIND_USER_PREFIX = "SOCKET_REMIND_USER_";

    /**
     * win 系统
     */
    public static final String WIN = "win";

    /**
     * mac 系统
     */
    public static final String MAC = "mac";

    /**
     * 定义GB的计算常量
     */
    public static final int GB = 1024 * 1024 * 1024;
    /**
     * 定义MB的计算常量
     */
    public static final int MB = 1024 * 1024;
    /**
     * 定义KB的计算常量
     */
    public static final int KB = 1024;

    // 发送短信数目
    public static final String SEND_MESSAGE_NUM_PREFIX = "SEND_MESSAGE_NUM_";

}
