package com.binance.client.future.impl.utils;

@FunctionalInterface
public interface Handler<T> {

  void handle(T t);
}
