package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

import static com.mzwise.constant.DistributionCommunityRulesConstant.*;

/**
 * @Author piao
 * @Date 2021/05/20
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum QuantificationLevelEnum implements IEnum<Integer> {
    LEVEL0(0, "LEVEL0"),
    LEVEL1(1, "LEVEL1"),
    LEVEL2(2, "LEVEL2"),
    LEVEL3(3, "LEVEL3"),
    LEVEL4(4, "LEVEL4"),
    LEVEL5(5, "LEVEL5"),
    LEVEL6(6, "LEVEL6"),
    LEVEL7(7, "LEVEL7");


    private final Integer value;
    private final String name;

    public static BigDecimal getCommunityAwardRateByLevel(QuantificationLevelEnum levelEnum) {
        BigDecimal rate = COMMUNITY_RATE_OF_LEVEL0;
        switch (levelEnum) {
            case LEVEL0:
                rate = COMMUNITY_RATE_OF_LEVEL0;
                break;
            case LEVEL1:
                rate = COMMUNITY_RATE_OF_LEVEL1;
                break;
            case LEVEL2:
                rate = COMMUNITY_RATE_OF_LEVEL2;
                break;
            case LEVEL3:
                rate = COMMUNITY_RATE_OF_LEVEL3;
                break;
            case LEVEL4:
                rate = COMMUNITY_RATE_OF_LEVEL4;
                break;
            case LEVEL5:
                rate = COMMUNITY_RATE_OF_LEVEL5;
                break;
            case LEVEL6:
                rate = COMMUNITY_RATE_OF_LEVEL6;
                break;
            case LEVEL7:
                rate = COMMUNITY_RATE_OF_LEVEL7;
                break;
            default:
        }
        return rate;
    }
}
