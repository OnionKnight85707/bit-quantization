package com.mzwise.huobi.market.socket.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum PeriodEnum {

  MIN1("1min") {
    @Override
    public long gapTime() {
      return 60l;
    }
  },
  MIN5("5min") {
    @Override
    public long gapTime() {
      return 5*60l;
    }
  },
  MIN15("15min") {
    @Override
    public long gapTime() {
      return 15*60l;
    }
  },
  MIN30("30min") {
    @Override
    public long gapTime() {
      return 30*60l;
    }
  },
  MIN60("60min") {
    @Override
    public long gapTime() {
      return 60*60l;
    }
  },
//  DAY1("1day") {
//    @Override
//    public long gapTime() {
//      return 24*60*60l;
//    }
//  }
  ;

  public static PeriodEnum fromCode(String code) {
    Optional<PeriodEnum> first = Arrays.stream(PeriodEnum.values()).filter(v -> v.getCode().equals(code)).findFirst();
    return first.get();
  }

  private final String code;

  PeriodEnum(String code) {
    this.code = code;
  }

  public long gapTime() {
    return 0L;
  }

}
