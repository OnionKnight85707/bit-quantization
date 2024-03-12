package com.bte.bipay.entity;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SupportCoin {
    private String name;
    private String symbol;
    private String mainCoinType;
    private String coinType;
    private String decimals;
    private Integer tokenStatus;
    private String mainSymbol;
    private BigDecimal balance;

    public static SupportCoin parse(String json){
        return JSON.parseObject(json,SupportCoin.class);
    }
}
