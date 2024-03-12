package com.binance.client.examples.swap.market;

import com.binance.client.future.FutureRequestOptions;
import com.binance.client.future.FutureRequestClient;

import com.binance.client.examples.swap.constants.PrivateConfig;
import com.binance.client.future.model.market.ExchangeInfoEntry;
import com.binance.client.future.model.market.ExchangeInformation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GetExchangeInformation {
    public static void main(String[] args) {
        FutureRequestOptions options = new FutureRequestOptions();
        FutureRequestClient futureRequestClient = FutureRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        ExchangeInformation exchangeInformation = futureRequestClient.getExchangeInformation();
        List<ExchangeInfoEntry> symbols = exchangeInformation.getSymbols();
        Optional<ExchangeInfoEntry> first = symbols.stream().filter(v -> v.getSymbol().equals("FUNUSDT")).findFirst();
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
        if (!lotSize.isPresent()) {
            return;
        }
        List<Map<String, String>> lotSizeItems = lotSize.get();
        System.out.println(lotSizeItems);
    }
}
