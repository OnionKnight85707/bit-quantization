package com.mzwise.unifyexchange.util;

/**
 * 将交易对转换成交易所需要的格式
 */
public class SymbolUtil {

    public static String asBinance(String symbol) {
        if (symbol==null)
            return null;
        return symbol.replace("/", "");
    }

    /**
     *  okex  : instId -> DOT-USDT-SWAP
     * @param symbol
     * @return
     */
    public static String toPlatform(String symbol) {
        if (symbol.contains("/"))
        {
            return symbol.toUpperCase();
        }
        symbol=symbol.replace("SWAP","").replace("-","");
        return symbol.toUpperCase().replace("USDT", "/USDT");
    }

    public static String asHuobi(String symbol) {
        return symbol.replace("/", "").toLowerCase();
    }

    public static String asOkex(String symbol) {
        return symbol.replace("/", "-");
    }
    public static String asOkexSwap(String symbol) {
        return symbol.replace("/", "-")+"-SWAP";
    }
}
