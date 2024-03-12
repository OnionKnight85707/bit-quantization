package com.mzwise.unifyexchange.common;

@FunctionalInterface
public interface StreamListener<T> {

  void onReceive(T data);
}
