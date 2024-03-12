package com.mzwise.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum MaEnum {


  MA9(9) ,
  MA22(22) ,
  MA34(34) ,
  MA55(55) ,
  MA60(60) ;


  public static MaEnum fromCode(Integer code) {
    Optional<MaEnum> first = Arrays.stream(MaEnum.values()).filter(v -> v.getCode().equals(code)).findFirst();
    return first.get();
  }

  private final Integer code;

  MaEnum(Integer code) {
    this.code = code;
  }

}
