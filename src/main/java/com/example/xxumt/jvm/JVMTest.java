package com.example.xxumt.jvm;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/10/9 17:46
 * @since 1.0
 */
public class JVMTest {
  public static void main(String[] args) {
    //
    /*int i = 1;
    int j = 2;
    int k = i + j;
    System.out.println(k);*/
    long initialMemory = Runtime.getRuntime().totalMemory() / 1024 /1024;
    long maxMemory = Runtime.getRuntime().maxMemory() / 1024 /1024;

    System.out.println("-Xms: " + initialMemory + "M");
    System.out.println("-Xmm: " + maxMemory + "M");

    System.out.println("system memory: " + initialMemory * 64 / 1024 + "G");
    System.out.println("system memory: " + maxMemory * 4 / 1024 + "G");
  }
}
