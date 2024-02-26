package com.example.xxumt.demo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/1/11 10:27
 * @since 1.0
 */
public class OutOfMemoryTest {
    private static final String THREAD_FACTORY_NAME_PRODUCT = "product";

    static class MyThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);

        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final String threadFactoryName;

        public String getThreadFactoryName() {
            return threadFactoryName;
        }

        MyThreadFactory(String threadStartName) {
            SecurityManager s = System.getSecurityManager();
            group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = threadStartName + "-pool-" + poolNumber.getAndIncrement() + "-thread-";
            threadFactoryName = threadStartName;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    /**
     * 任务被拒接不抛异常 任务线程状态仍为NEW
     * 任务被拒接抛异常 任务线程状态从NEW -> COMPLETING -> EXCEPTION
     */
    static class MyRejectPolice implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (executor.getThreadFactory() instanceof MyThreadFactory) {
                MyThreadFactory threadFactory = (MyThreadFactory) executor.getThreadFactory();
                if (THREAD_FACTORY_NAME_PRODUCT.equals(threadFactory.getThreadFactoryName())) {
                    System.out.println(THREAD_FACTORY_NAME_PRODUCT + "线程池有任务被拒绝了,请关注");
                }
            }
            // 拒绝时抛出异常，解决内存泄漏
            // throw new RejectedExecutionException("Task " + r.toString() + "reject from " + executor);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //线程池一次只允许一个任务运行
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
                1,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                new MyThreadFactory("product"),
                new MyRejectPolice());

        // 模拟定时任务每秒执行一次
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            new Thread(() -> {
                ArrayList<Future<Integer>> futureList = new ArrayList<>();
                int product = 5;
                for (int i = 0; i < product; i++) {
                    int finalI = i;
                    try {
                        Future<Integer> future = executor.submit(() -> {
                            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
                            return finalI * 10;
                        });
                        // 拒绝策略不抛异常所有任务都被添加
                        futureList.add(future);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                for (Future<Integer> future : futureList) {
                    final Integer integer;
                    try {
                        // 被拒绝的任务不会被线程池触发run方法，该future状态不会从NEW状态变化为EXCEPTION｜NORMAL
                        // 所以执行future.get()方法会阻塞，而又因为是定时任务触发的逻辑，所以会导致future对象越来越多形成内存泄露
                        integer = future.get();
                        System.out.println("future.get() = " + integer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
