package com.mzwise.common.api;

/**
 * 枚举了一些常用API操作码
 * Created by admin on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "success"),
    IGNORED(300, "ignored"),
    FAILED(500, "failed"),
    VALIDATE_FAILED(404, "validate_failed"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    CUSTOM10001(10001, "CUSTOM1"),
    CUSTOM10002(10002, "CUSTOM2"),
    CUSTOM10003(10003, "CUSTOM3");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
