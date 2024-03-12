package com.mzwise.constant;

public class TradeClientId {


    public static final  String  binanceClientPre="x-rzZnwyqM";

    public static final  String  huobiClientPre="x-rzZnwyqM";

    public static final  String  okexClientPre="XXX";


    public static boolean isTradeClientId(String orderId)
    {
        if (orderId.startsWith(binanceClientPre))
            return  true;

        if (orderId.startsWith(huobiClientPre))
            return  true;

        if (orderId.startsWith(okexClientPre))
            return  true;

        return false;
    }

    public static String removePre(String orderId)
    {
        if (orderId.startsWith(binanceClientPre))
        {
            return orderId.replace(binanceClientPre,"");
        }
        if (orderId.startsWith(huobiClientPre))
        {
            return orderId.replace(huobiClientPre,"");
        }
        if (orderId.startsWith(okexClientPre))
        {
            return orderId.replace(okexClientPre,"");
        }
        return null;
    }



}
