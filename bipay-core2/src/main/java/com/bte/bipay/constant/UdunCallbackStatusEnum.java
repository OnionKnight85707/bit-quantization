package com.bte.bipay.constant;

/**
 * 优盾回调状态枚举
 */
public enum UdunCallbackStatusEnum {

    PENDING_REVIEW(0,"待审核"),
    REVIEW_SUCCEEDED(1,"审核成功"),
    REVIEW_REJECTED(2,"审核驳回"),
    TRANSACTION_SUCCESSFUL(3,"交易成功"),
    TRANSACTION_FAILED(4,"交易失败");

    private final Integer code;
    private final String desc;

    UdunCallbackStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UdunCallbackStatusEnum findByCode(Integer code) {
        for (UdunCallbackStatusEnum temp : UdunCallbackStatusEnum.values()) {
            if (temp.getCode() == code) {
                return temp;
            }
        }
        return null;
    }
}
