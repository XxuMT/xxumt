package com.example.xxumt.io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/7/7 16:05
 * @since 1.0
 */
public class Handler implements Runnable{
    private final SocketChannel channel;
    private volatile static Selector selector;
    private SelectionKey key;
    private volatile ByteBuffer input = ByteBuffer.allocate(1024);
    private volatile ByteBuffer output = ByteBuffer.allocate(1024);

    public Handler(SocketChannel channel) throws IOException {
        this.channel = channel;
        channel.configureBlocking(false);
        selector = Selector.open();
        key = channel.register(selector, SelectionKey.OP_READ);
    }

    @Override
    public void run() {
        try {
            while (selector.isOpen() && channel.isOpen()) {
                final Set<SelectionKey> keys = select();
                final Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    final SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Set<SelectionKey> select() throws IOException{
        selector.select();
        final Set<SelectionKey> keys = selector.selectedKeys();
        if (keys.isEmpty()) {
            final int interestOps = key.interestOps();
            selector = Selector.open();
            key = channel.register(selector, interestOps);
            return select();
        }
        return keys;
    }

    private void read(SelectionKey key) throws IOException {
        channel.read(input);
        if (input.position() == 0) {
            return;
        }

        input.flip();
        process();
        input.clear();
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void write(SelectionKey key) throws IOException {
        output.flip();
        if (channel.isOpen()) {
            channel.write(output);
            key.channel();
            channel.close();
            output.clear();
        }
    }

    // 业务处理
    private void process() {

    }
}
