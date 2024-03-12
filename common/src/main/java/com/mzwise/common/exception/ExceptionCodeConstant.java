package com.mzwise.common.exception;

/**
 * 异常代码常量
 * @author: David Liang
 * @create: 2022-07-23 17:49
 */
public class ExceptionCodeConstant {

    /**
     * AliYunSMSProvider类
     */
    // 地区码错误
    public static String AliYunSMSProvider_001 = "AliYunSMSProvider_001";
    // 验证码获取频繁，请稍后再试
    public static String AliYunSMSProvider_002 = "AliYunSMSProvider_002";

    /**
     *  UcWithdrawServiceImpl类
     */
    //提币数额不得小于最小提币数量
    public static String UcWithdrawServiceImpl_001="UcWithdrawServiceImpl_001";
    // 余额不足
    public static String UcWithdrawServiceImpl_002="UcWithdrawServiceImpl_002";

    /**
     *  SecurityUtils
     */
    //当前登录状态过期
    public static String SecurityUtils_001="SecurityUtils_001";

    /**
     *  QuantApiAccessServiceImpl
     */
    // API已被绑定
    public static String QuantApiAccessServiceImpl_001="QuantApiAccessServiceImpl_001";
    // API访问错误
    public static String QuantApiAccessServiceImpl_002="QuantApiAccessServiceImpl_002";
    // API访问异常
    public static String QuantApiAccessServiceImpl_003="QuantApiAccessServiceImpl_003";



    /**
     * UcMemberController
     */
    // 密码格式错误
    public static String UcMemberController_001="UcMemberController_001";
    // 验证码错误
    public static String UcMemberController_002="UcMemberController_002";
    // 用户名或密码错误
    public static String UcMemberController_003="UcMemberController_003";
    // 用户不存在
    public static String UcMemberController_004="UcMemberController_004";
    // 原密码不能为空
    public static String UcMemberController_005="UcMemberController_005";
    // 新密码不能为空
    public static String UcMemberController_006="UcMemberController_006";
    // 原密码错误
    public static String UcMemberController_007="UcMemberController_007";
    // 丢失旧密码
    public static String UcMemberController_009="UcMemberController_009";
    // 缺少新的资金密码
    public static String UcMemberController_010="UcMemberController_010";
    // 资金密码长度错误
    public static String UcMemberController_011="UcMemberController_011";
    // 资金密码错误
    public static String UcMemberController_012="UcMemberController_012";
    // 用户尚未设置交易密码
    public static String UcMemberController_013="UcMemberController_013";
    // 用户不存在该下级合伙人
    public static String UcMemberController_014="UcMemberController_014";
    // 用户需要绑定该密钥
    public static String UcMemberController_015="UcMemberController_015";
    // 用户已绑定谷歌私钥
    public static String UcMemberController_016="UcMemberController_016";
    // 该用户未登录
    public static String UcMemberController_017="UcMemberController_017";
    // 谷歌私钥与验证码不匹配，绑定失败 请重新输入验证码
    public static String UcMemberController_018="UcMemberController_018";
    // 用户输入验证码错误！请重新获取
    public static String UcMemberController_019="UcMemberController_019";
    // 取消绑定失败
    public static String UcMemberController_020="UcMemberController_020";
    // 不允许将合伙人设置成非合伙人
    public static String UcMemberController_021="UcMemberController_021";


    /**
     *  UcMemberServiceImpl
     */
    // 用户名错误
    public static String UcMemberServiceImpl_001="UcMemberServiceImpl_001";
    // 手机号错误
    public static String UcMemberServiceImpl_002="UcMemberServiceImpl_002";
    // 邮箱错误
    public static String UcMemberServiceImpl_003="UcMemberServiceImpl_003";
    // 该号码已被使用
    public static String UcMemberServiceImpl_004="UcMemberServiceImpl_004";

    /**
     *  HomeQuestionnaireController
     */
    // 参数错误
    public static String HomeQuestionnaireController_001="HomeQuestionnaireController_001";

    /**
     *  UcMessageCenterController
     */
    // 未经授权
    public static String UcMessageCenterController_001="UcMessageCenterController_001";


    /**
     *  QuantStrategyController
     */
    // 参数错误, 未选择方向
    public static String QuantStrategyController_001="QuantStrategyController_001";
    // 仓位太小，最小仓位为：
    public static String QuantStrategyController_002="QuantStrategyController_002";
    // 当前指标尚未激活！请激活后重试
    public static String QuantStrategyController_003="QuantStrategyController_003";
    // 该技术指标无默认参数
    public static String QuantStrategyController_004="QuantStrategyController_004";
    // 当前激活状态下无法直接删除策略
    public static String QuantStrategyController_005="QuantStrategyController_005";
    // 该策略不属于当前账户
    public static String QuantStrategyController_006="QuantStrategyController_006";
    // 仓位必须为正数
    public static String QuantStrategyController_007="QuantStrategyController_007";
    // 杠杆不能为负数
    public static String QuantStrategyController_008="QuantStrategyController_008";
    // 该API已存在策略
    public static String QuantStrategyController_009 = "QuantStrategyController_009";
    /**
     *  QuantPoints
     */
     // 自设指标类型严重错误
    public static String QuantPoints_001="QuantPoints_001";

    /**
     *  QuantStrategyServiceImpl
     */
    // 已存在该策略
    public static String QuantStrategyServiceImpl_001="QuantStrategyServiceImpl_001";
    // api为空
    public static String QuantStrategyServiceImpl_002="QuantStrategyServiceImpl_002";
    // 交易对不可用
    public static String QuantStrategyServiceImpl_003="QuantStrategyServiceImpl_003";
    // 网格数不正确
    public static String QuantStrategyServiceImpl_004="QuantStrategyServiceImpl_004";
    // 佣金价格太高
    public static String QuantStrategyServiceImpl_005="QuantStrategyServiceImpl_005";
    // 佣金价格太低
    public static String QuantStrategyServiceImpl_006="QuantStrategyServiceImpl_006";
    // 仓位太低
    public static String QuantStrategyServiceImpl_007="QuantStrategyServiceImpl_007";
    // 策略不存在
    public static String QuantStrategyServiceImpl_008="QuantStrategyServiceImpl_008";
    // 激活状态下不能修改指标
    public static String QuantStrategyServiceImpl_009="QuantStrategyServiceImpl_009";
    // 修改量化策略：策略类型错误
    public static String QuantStrategyServiceImpl_010="QuantStrategyServiceImpl_010";
    // 仓位金额不能为负数
    public static String QuantStrategyServiceImpl_011="QuantStrategyServiceImpl_011";
    // 激活状态下的策略
    public static String QuantStrategyServiceImpl_012="QuantStrategyServiceImpl_012";


    /**
     *  QuantMemberService
     */
    // 服务费余额不足，请充值
    public static String QuantMemberService_001="QuantMemberService_001";


    /**
     *  CustomVerifyRequiredAspect
     */
    // 未绑定绑定币安验证器
    public static String CustomVerifyRequiredAspect_001="CustomVerifyRequiredAspect_001";
    // 缺少币安验证码
    public static String CustomVerifyRequiredAspect_002="CustomVerifyRequiredAspect_002";
    // 币安验证码错误
    public static String CustomVerifyRequiredAspect_003="CustomVerifyRequiredAspect_003";
    // 未绑定邮箱
    public static String CustomVerifyRequiredAspect_004="CustomVerifyRequiredAspect_004";
    // 缺少邮箱验证码
    public static String CustomVerifyRequiredAspect_005="CustomVerifyRequiredAspect_005";
    // 邮箱验证码错误
    public static String CustomVerifyRequiredAspect_006="CustomVerifyRequiredAspect_006";
    // 未绑定手机
    public static String CustomVerifyRequiredAspect_007="CustomVerifyRequiredAspect_007";
    // 缺少手机验证码
    public static String CustomVerifyRequiredAspect_008="CustomVerifyRequiredAspect_008";
    // 手机验证码错误
    public static String CustomVerifyRequiredAspect_009="CustomVerifyRequiredAspect_009";
    // 请开启币安验证或者手机验证
    public static String CustomVerifyRequiredAspect_010="CustomVerifyRequiredAspect_010";

    /**
     *  UcenterMemberServiceImpl
     */
    //  用户不可用
    public static String UcenterMemberServiceImpl_001="UcenterMemberServiceImpl_001";
    // 密码格式错误
    public static String UcenterMemberServiceImpl_002="UcenterMemberServiceImpl_002";
    // 密码错误
    public static String UcenterMemberServiceImpl_003="UcenterMemberServiceImpl_003";
    // 超出最大合伙人返佣比例
    public static String UcenterMemberServiceImpl_004="UcenterMemberServiceImpl_004";
    // 您必须先成为合伙人才能设置下级合伙人
    public static String UcenterMemberServiceImpl_005="UcenterMemberServiceImpl_005";
    // 不能超出最小佣金设置范围
    public static String UcenterMemberServiceImpl_006="UcenterMemberServiceImpl_006";
//    // 上级的返佣额度为
//    public static String UcenterMemberServiceImpl_007="UcenterMemberServiceImpl_007";
    // 返佣额度不能大于上级的返佣额度:
    public static String UcenterMemberServiceImpl_008="UcenterMemberServiceImpl_008";
    // 下级最大返佣额度为:
    public static String UcenterMemberServiceImpl_009="UcenterMemberServiceImpl_009";
    // ,返佣额度不能小于该下级
    public static String UcenterMemberServiceImpl_010="UcenterMemberServiceImpl_010";

    /**
     * WalletServiceImpl
     */
    // 交易对手账户不存在或已被冻结
    public static String WalletServiceImpl_001="WalletServiceImpl_001";
    // 没有设置资金密码
    public static String WalletServiceImpl_002="WalletServiceImpl_002";
    // 资金密码错误
    public static String WalletServiceImpl_003="WalletServiceImpl_003";
    // 余额不足
    public static String WalletServiceImpl_004="WalletServiceImpl_004";
    // 不支持的货币
    public static String WalletServiceImpl_005="WalletServiceImpl_005";

    /**
     *  QuantStrategyTrend
     */
    // 数据出现严重错误,请检查
    public static String QuantStrategyTrend_001="QuantStrategyTrend_001";
    // 首单方式请输入正值
    public static String QuantStrategyTrend_002="QuantStrategyTrend_002";
    // 首单额度请输入正值
    public static String QuantStrategyTrend_003="QuantStrategyTrend_003";
    // 止盈比例请输入正值
    public static String QuantStrategyTrend_004="QuantStrategyTrend_004";
    // 止盈回调请输入正值
    public static String QuantStrategyTrend_005="QuantStrategyTrend_005";
    // 止损比例请输入正值
    public static String QuantStrategyTrend_006="QuantStrategyTrend_006";
    // 补仓次数请输入正值
    public static String QuantStrategyTrend_007="QuantStrategyTrend_007";

    /**
     *  MaiXunTongSMSProvider
     */
    // 短信数据有误
    public static String MaiXunTongSMSProvider_001="MaiXunTongSMSProvider_001";
    // 短信发送失败
    public static String MaiXunTongSMSProvider_002="MaiXunTongSMSProvider_002";
    // 请输入正确的号码
    public static String MaiXunTongSMSProvider_003="MaiXunTongSMSProvider_003";

    /**
     * SmsController
     */
    // 每日发送短信超限
    public static String SmsController_001="SmsController_001";

    /**
     *  GoogleAuthenticatorService
     */
    // 验证码错误
    public static String GoogleAuthenticatorService_001="GoogleAuthenticatorService_001";

    /**
     *  PayPasswordRequiredAspect
     */
    // 资金密码错误
    public static String PayPasswordRequiredAspect_001="PayPasswordRequiredAspect_001";
    // 未设置资金密码
    public static String PayPasswordRequiredAspect_002="PayPasswordRequiredAspect_002";
    // 未输入资金密码
    public static String PayPasswordRequiredAspect_003="PayPasswordRequiredAspect_003";


    /**
     *  QuantStrategyUnified
     */
    // 统一策略：point转换实体错误
    public static String QuantStrategyUnified_001="QuantStrategyUnified_001";

    /**
     *  UnifyExchangeUtil
     */
    // 交易所余額不足，無法滿足當前倉位，請及時充值！ insufficient_exchange_balance
    public static String UnifyExchangeUtil_001="UnifyExchangeUtil_001";
    // account_error 賬號異常
    public static String UnifyExchangeUtil_002="UnifyExchangeUtil_002";
    // platform_trade_type_not_support 平臺交易類型不支持
    public static String UnifyExchangeUtil_003="UnifyExchangeUtil_003";

    /**
     * EmailController
     */
    //郵箱已被使用
    public static String EmailController_001="EmailController_001";

}
