package com.example.xxumt.io.nio;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/7/7 15:53
 * @since 1.0
 */
public class Acceptor implements Runnable{

    private final ExecutorService executor = Executors.newFixedThreadPool(20);

    private final ServerSocketChannel serverSocket;

    public Acceptor(ServerSocketChannel serverSocket) {
        this.serverSocket = serverSocket;
    }
    @Override
    public void run() {
        try {
            SocketChannel channel = serverSocket.accept();
            if (null != channel) {
                // 将客户端连接交由线程池处理
                executor.execute(new Handler(channel));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
