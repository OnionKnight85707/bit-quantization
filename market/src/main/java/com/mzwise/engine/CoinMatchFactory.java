package com.mzwise.engine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CoinMatchFactory {

    /** symbol, match **/
    private ConcurrentHashMap<String, CoinMatch> matchMap;

    public CoinMatchFactory() {
        this.matchMap = new ConcurrentHashMap<>();
    }

    public void addCoinMatch(String symbol, CoinMatch match) {
        log.info("CoinMatchFactory add match = {}", symbol);
        if (!this.containsCoinMatch(symbol)) {
            this.matchMap.put(symbol, match);
        }
    }

    public boolean containsCoinMatch(String symbol) {
        return this.matchMap != null && this.matchMap.containsKey(symbol);
    }

    public CoinMatch getCoinMatch(String symbol) {
        return this.matchMap.get(symbol);
    }

    public Map<String, CoinMatch> getMatchMap() {
        return this.matchMap;
    }
}
