package com.example.xxumt.juc;

import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueue消费类
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/12/28 15:03
 * @since 1.0
 */
public class Consumer implements Runnable {
  protected BlockingQueue queue = null;

  public Consumer(BlockingQueue queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      System.out.println(queue.take());
      System.out.println(queue.take());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
