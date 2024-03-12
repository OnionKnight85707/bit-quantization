package com.mzwise.common.vo.macd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MacdInQuota {

    private Integer fastPeriod;

    private Integer slowPeriod;

    private Integer signalPeriod;
}
