package com.imooc.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

public class CommonUtils {

  /**
   * 传入指定 factory ，若 map 的 value 不存在。则指定 value 为 factory
   */
  public static <K, V> V getorCreate(K key, Map<K, V> map,
                                     Supplier<V> factory) {
    return map.computeIfAbsent(key, k -> factory.get());
  }

  /**
   * 以 - 拼接字符串参数。 a-b-c
   */
  public static String stringConcat(String... args) {
    StringBuilder builder = new StringBuilder();
    for (String arg : args) {
      builder.append(arg);
      builder.append("-");
    }
    builder.deleteCharAt(builder.length() - 1);
    return builder.toString();
  }
}
