package com.binance.client.spot.impl.utils;

@FunctionalInterface
public interface Handler<T> {

  void handle(T t);
}
