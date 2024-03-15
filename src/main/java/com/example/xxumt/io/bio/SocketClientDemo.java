package com.example.xxumt.io.bio;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.concurrent.CountDownLatch;

/**
 * 模拟客户端Demo
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/4/20 10:34
 * @since 1.0
 */
@EnableAspectJAutoProxy
public class SocketClientDemo {
    public static void main(String[] args) throws Exception {
        int clientNum = 20;
        CountDownLatch countDownLatch = new CountDownLatch(clientNum);

        for (int index = 0; index < clientNum; index++, countDownLatch.countDown()) {
            SocketClientRequestThread client = new SocketClientRequestThread(countDownLatch, index);
            new Thread(client).start();
        }

        synchronized (SocketClientDemo.class) {
            SocketClientDemo.class.wait();
        }
    }
}
