package com.example.xxumt.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/12/28 15:04
 * @since 1.0
 */
public class BlockingQueueDemo {

  public static void main(String[] args) throws InterruptedException {
    BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1024);

    Producer producer = new Producer(queue);
    Consumer consumer = new Consumer(queue);

    new Thread(producer).start();
    new Thread(consumer).start();

    Thread.sleep(3000);
  }
}
