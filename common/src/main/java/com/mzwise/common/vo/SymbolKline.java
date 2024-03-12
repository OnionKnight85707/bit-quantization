package com.mzwise.common.vo;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SymbolKline {

    private List<KLine> kLines;

    private String symbol;


}
