package com.example.xxumt.thread;

/**
 * 同感wait/notify实现同步 必须先调用wait再调用notify，否则不起作用
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/12/12 16:59
 * @since 1.0
 */
public class WaitAndNotifyDemo {
  public static void main(String[] args) throws InterruptedException {
    MyThread thread = new MyThread();
    synchronized (thread) {
      try {
        thread.start();
        Thread.sleep(3000);
        System.out.println("before wait");
        // 阻塞线程
        thread.wait();
        System.out.println("after wait");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

class MyThread extends Thread {
  @Override
  public void run() {
    synchronized (this) {
      System.out.println("before notify");
      notify();
      System.out.println("after notify");
    }
  }
}
