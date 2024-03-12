package com.mzwise.common.vo.macd;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MacdOutQuota {

    private long[] time;

    private double[] macd;

    private double[] dif;

    private double[] dea;

}
