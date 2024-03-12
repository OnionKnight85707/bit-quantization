package com.mzwise.common.vo.kdj;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;

@Data
@Builder
public class KdjOutQuota {

    private double[] k;

    private double[] d;

    private double[] j;

    @Override
    public String toString() {
        return "{\"KdjOutQuota\":{"
                + "\"k\":"
                + Arrays.toString(k)
                + ",\"d\":"
                + Arrays.toString(d)
                + ",\"j\":"
                + Arrays.toString(j)
                + "}}";

    }
}
