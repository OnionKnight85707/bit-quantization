package com.mzwise.unifyexchange.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mzwise.unifyexchange.beans.SymbolInfo;
import com.mzwise.unifyexchange.common.Constants;
import com.okex.open.api.config.APIConfiguration;
import com.okex.open.api.service.publicData.PublicDataAPIService;
import com.okex.open.api.service.publicData.impl.PublicDataAPIServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Data
@Slf4j
public class OkexUtil {

    private static Map<String ,BigDecimal> sizeMap=new HashMap<>();

    public static  BigDecimal getContractSize(String symbol)
    {
        return  sizeMap.get(symbol);
    }

    static {

        if (sizeMap==null || sizeMap.size()==0)
        {
            log.info("获取okex  合约面值...");

            sizeMap=getAllSwapSize();
            log.info("合约面值信息 条数：{}",sizeMap.size());
        }

    }

    public static JSONObject getSimpleResponse(JSONObject body) {
        JSONArray data = body.getJSONArray("data");
        return data.getJSONObject(0);
    }

    /**
     * 获取OKEX  U本位合约 每张 代表的数量
     * @return
     */
    public static Map<String ,BigDecimal> getAllSwapSize ()
    {
        Map<String ,BigDecimal> map=new HashMap<>();
        SymbolInfo.Response response = new SymbolInfo.Response();
        try {
            APIConfiguration config = new APIConfiguration();
            PublicDataAPIService publicDataAPIService = new PublicDataAPIServiceImpl(config);
            JSONObject result = publicDataAPIService.getInstruments("SWAP", null);
            JSONArray data = result.getJSONArray("data");
            for (Object detail : data) {
                Map<String,String> asset = (Map<String,String>) detail;
                map.put(SymbolUtil.toPlatform(asset.get("instId")),new BigDecimal(asset.get("ctVal")));
            }


            return map;
        } catch (Exception e) {
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
           log.error(" 获取OKEX 币种 张数配置 错误 !");

            return map;
        }
    }

}
