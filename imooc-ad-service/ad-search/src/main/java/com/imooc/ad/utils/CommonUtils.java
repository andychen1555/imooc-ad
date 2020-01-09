package com.imooc.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

public class CommonUtils {

  /**
   * 传入指定 factory ，若 map 的 value 不存在。则指定 value 为
   * @param key
   * @param map
   * @param factory
   * @param <K>
   * @param <V>
   * @return
   */
  public static <K, V> V getorCreate(K key, Map<K, V> map,
                                     Supplier<V> factory) {
    return map.computeIfAbsent(key, k -> factory.get());
  }
}
