package com.bte.bipay.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class Transaction {
    private String transaction;
    public static Transaction parse(String json){
        return JSON.parseObject(json,Transaction.class);
    }
}
