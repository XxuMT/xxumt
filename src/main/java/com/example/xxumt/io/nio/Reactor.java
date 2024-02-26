package com.example.xxumt.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 并发读写Reactor模型
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/7/7 15:18
 * @since 1.0
 */
public class Reactor implements Runnable{

    private final Selector selector;
    private final ServerSocketChannel serverSocket;

    public Reactor(int port) throws IOException {
        serverSocket = ServerSocketChannel.open();
        serverSocket.configureBlocking(false);
        serverSocket.bind(new InetSocketAddress(port));
        selector = Selector.open();
        SelectionKey key = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        // 为服务端Channel绑定一个Acceptor
        key.attach(new Acceptor(serverSocket));
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                final Set<SelectionKey> keys = selector.selectedKeys();
                final Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    dispatch(iterator.next());
                    iterator.remove();
                }

                selector.selectNow();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey key) throws IOException {
        Runnable attachment = (Runnable) key.attachment();
        attachment.run();
    }
}
