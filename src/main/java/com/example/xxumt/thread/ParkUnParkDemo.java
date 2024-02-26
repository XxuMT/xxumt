package com.example.xxumt.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/12/13 10:21
 * @since 1.0
 */
public class ParkUnParkDemo {
  public static void main(String[] args) {
    MyThread2 myThread2 = new MyThread2(Thread.currentThread());
    myThread2.start();
    System.out.println("before park");
    // 获取许可
    LockSupport.park("ParkUnParkDemo");
    System.out.println("after park");
  }
}

class MyThread2 extends Thread {
  private Object object;

  public MyThread2(Object object) {
    this.object = object;
  }

  @Override
  public void run() {
    System.out.println("before unpark");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Blocker info " + LockSupport.getBlocker((Thread) object));
    // 释放许可
    LockSupport.unpark((Thread) object);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Blocker info " + LockSupport.getBlocker((Thread) object));
    System.out.println("after unpark");
  }
}
