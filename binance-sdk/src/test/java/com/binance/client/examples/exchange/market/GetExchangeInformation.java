package com.binance.client.examples.exchange.market;

import com.binance.client.examples.exchange.constants.PrivateConfig;
import com.binance.client.spot.SpotRequestClient;
import com.binance.client.spot.SpotRequestOptions;
import com.binance.client.spot.model.market.ExchangeInfoEntry;
import com.binance.client.spot.model.market.ExchangeInformation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GetExchangeInformation {
    public static void main(String[] args) {
        SpotRequestOptions options = new SpotRequestOptions();
        SpotRequestClient spotRequestClient = SpotRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        ExchangeInformation exchangeInformation = spotRequestClient.getExchangeInformation();
        List<ExchangeInfoEntry> symbols = exchangeInformation.getSymbols();
        Optional<ExchangeInfoEntry> first = symbols.stream().filter(v -> v.getSymbol().equals("SHIBUSDT")).findFirst();
        if (!first.isPresent()) {
            return;
        }
        ExchangeInfoEntry exchangeInfo = first.get();
        List<List<Map<String, String>>> filters = exchangeInfo.getFilters();
        Optional<List<Map<String, String>>> lotSize = filters.stream().filter(
                f -> f.stream().anyMatch(
                        l -> l.entrySet().stream().anyMatch(
                                v -> v.getKey().equals("filterType") && v.getValue().equals("LOT_SIZE")
                        )
                )
        ).findFirst();
        List<Map<String, String>> lotSizeItems = lotSize.get();
        BigDecimal stepSize = new BigDecimal(lotSizeItems.stream().filter(l -> l.containsKey("stepSize")).findFirst().get().get("stepSize"));
        Integer assetScale = scaleBySize(stepSize);
        Optional<List<Map<String, String>>> priceFilter = filters.stream().filter(
                f -> f.stream().anyMatch(
                        l -> l.entrySet().stream().anyMatch(
                                v -> v.getKey().equals("filterType") && v.getValue().equals("PRICE_FILTER")
                        )
                )
        ).findFirst();
        List<Map<String, String>> priceFilterItems = priceFilter.get();
        BigDecimal tickSize = new BigDecimal(priceFilterItems.stream().filter(l -> l.containsKey("tickSize")).findFirst().get().get("tickSize"));
        Integer quotaScale = scaleBySize(tickSize);
        Optional<List<Map<String, String>>> minNotionalOp = filters.stream().filter(
                f -> f.stream().anyMatch(
                        l -> l.entrySet().stream().anyMatch(
                                v -> v.getKey().equals("filterType") && v.getValue().equals("MIN_NOTIONAL")
                        )
                )
        ).findFirst();
        List<Map<String, String>> minNotionalItems = minNotionalOp.get();
        BigDecimal minNotional = new BigDecimal(minNotionalItems.stream().filter(l -> l.containsKey("minNotional")).findFirst().get().get("minNotional"));
        System.out.println(assetScale);
        System.out.println(quotaScale);
        System.out.println(minNotional);
    }

    public static Integer scaleBySize(BigDecimal size) {
        int number = BigDecimal.ONE.divide(size).intValue();
        int count = 0;
        while (number > 1) {
            number = number / 10;
            count++;
        }
        return count;
    }
}
