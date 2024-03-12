package com.bte.bipay.constant;

/**
 * 优盾交易类型枚举
 */
public enum UdunTradeTypeEnum {

    DEPOSIT_CALLBACK(1,"充币回调"),
    WITHDRAWAL_CALLBACK(2,"提币回调");

    private final Integer code;
    private final String desc;

    UdunTradeTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UdunTradeTypeEnum findByCode(Integer code) {
        for (UdunTradeTypeEnum temp : UdunTradeTypeEnum.values()) {
            if (temp.getCode() == code) {
                return temp;
            }
        }
        return null;
    }
}
