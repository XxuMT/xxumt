package com.example.xxumt.current;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/1/9 16:30
 * @since 1.0
 */
public class AbstractQueuedSynchronizerDemo {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        MyThread thread1 = new MyThread("t1", lock);
        MyThread thread2 = new MyThread("t2", lock);

        thread1.start();
        thread2.start();


    }
}

class MyThread extends Thread {
    private Lock lock;
    public MyThread(String name, Lock lock) {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread() + "running");
        } finally {
            lock.unlock();
        }
    }
}
