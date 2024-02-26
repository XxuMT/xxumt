package com.example.xxumt.thread;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/12/1 16:50
 * @since 1.0
 */
public class SynchronizedDemo {
  Object object = new Object();

  public void method() {
    synchronized (object) {
      // javac SynchronizedDemo.java
      // javap -verbose SynchronizedDemo.class
    }
  }
}
