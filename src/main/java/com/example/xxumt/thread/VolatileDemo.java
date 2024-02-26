package com.example.xxumt.thread;

import java.util.concurrent.TimeUnit;

/**
 * volatile可见姓
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/12/2 14:28
 * @since 1.0
 */
public class VolatileDemo {
  private static boolean stop = false;

  public static void main(String[] args) {
    /*new Thread() {
      @Override
      public void run() {
        while (!stop) {
          System.out.println(Thread.currentThread() + "stopped");
        }
      }
    }.start();

    try {
      TimeUnit.SECONDS.sleep(1);
      System.out.println(Thread.currentThread() + "after 1 second");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    stop = true;*/

  }
}
