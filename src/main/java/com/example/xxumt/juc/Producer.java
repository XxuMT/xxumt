package com.example.xxumt.juc;

import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueue生产者
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/12/28 14:41
 * @since 1.0
 */
public class Producer implements Runnable{
    protected BlockingQueue<String> queue = null;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try{
            queue.put("hello");
            Thread.sleep(1000);
            queue.put("world");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
