package com.example.xxumt.lambda;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/8/18 10:43
 * @since 1.0
 */
public class TestLocalCache {

  private static ConcurrentHashMap<Integer, Long> cache = new ConcurrentHashMap<>();

  static long fibanacci(int i) {
    if (i == 0) {
      return i;
    }
    if (i == 1) {
      return 1;
    }
    return cache.computeIfAbsent(
        i,
        (key) -> {
          System.out.println("slow calculation of" + key);
          return fibanacci(i - 2) + fibanacci(i - 1);
        });
  }

  public static void main(String[] args) {
    //
    long current = System.currentTimeMillis();
    for (int i = 0; i < 101; i++) {
      System.out.println("f(" + i + ")" + fibanacci(i));
    }
    System.out.println(System.currentTimeMillis() - current);
    System.out.println(fibanacci(100));
  }
}
