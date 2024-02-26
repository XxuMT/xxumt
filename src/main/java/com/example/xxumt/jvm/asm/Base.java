package com.example.xxumt.jvm.asm;

import java.lang.management.ManagementFactory;

/**
 * 字节码运行类
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/10/8 11:20
 * @since 1.0
 */
public class Base {
  public static void main(String[] args) {
    String name = ManagementFactory.getRuntimeMXBean().getName();
    String s = name.split("@")[0];
    System.out.println("pid: " + s);
    while (true) {
      try {
        Thread.sleep(5000L);
      } catch (Exception e) {
        break;
      }
      process();
    }
  }

  public static void process() {
    System.out.println("process");
  }
}
