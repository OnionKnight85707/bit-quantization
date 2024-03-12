package com.mzwise.security.component;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStorage {


    private Map<String,String> tokenMap=new ConcurrentHashMap<>();


    public void addTokenMap(String userName,String token)
    {
        tokenMap.put(userName,token);
    }


    public String getTokenFromMap(String userName)
    {
        return tokenMap.get(userName);
    }

    public boolean contains(String token)
    {
        return tokenMap.containsValue(token);
    }

}
