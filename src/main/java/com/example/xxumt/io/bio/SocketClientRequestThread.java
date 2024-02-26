package com.example.xxumt.io.bio;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * 一个SocketClientRequestThread模拟一个客户端请求
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/2/8 10:42
 * @since 1.0
 */
@Slf4j
public class SocketClientRequestThread implements Runnable{

    static {
        BasicConfigurator.configure();
    }

    private CountDownLatch countDownLatch;

    private Integer clientIndex;

    /**
     * countDownLatch是java提供的同步计数器。
     * 当计数器数值减为0时，所有受其影响而等待的线程将会被激活。这样保证模拟并发请求的真实性
     * @param countDownLatch
     */
    public SocketClientRequestThread(CountDownLatch countDownLatch, Integer clientIndex) {
        this.countDownLatch = countDownLatch;
        this.clientIndex = clientIndex;
    }

    @Override
    public void run() {
        Socket socket = null;
        OutputStream clientRequest = null;
        InputStream clientResponse = null;

        try {
            socket = new Socket("localhost", 83);
            clientRequest = socket.getOutputStream();
            clientResponse = socket.getInputStream();

            this.countDownLatch.await();

            clientRequest.write(("这是第" + this.clientIndex + " 个客户端请求").getBytes());
            clientRequest.flush();

            log.info("第" + this.clientIndex + "个客户端请求发送完成，等待服务器返回结果");
            int maxLen = 1024;
            byte[] contextBytes = new byte[maxLen];
            int realLen;
            String message = "";
            while ((realLen = clientResponse.read(contextBytes, 0, maxLen)) != -1) {
                message += new String(contextBytes, 0, realLen);
            }
            log.info("接收到来自服务器的信息：" + message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (null != clientRequest) {
                    clientRequest.close();
                }
                if (null != clientResponse) {
                    clientResponse.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
