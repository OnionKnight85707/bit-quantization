package com.mzwise.common.vo;

import lombok.Data;


@Data
public class MaItem {

    private float maPrice;

    /**
     * 向上还是向下
     */
    private  boolean up;


    private float last1Percent;

    private float last2Percent;

    private float last3Percent ;

    private float last4Percent;

    private float last5Percent ;



}
