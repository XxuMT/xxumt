package com.example.xxumt.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 套接字实例Server
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/2/28 16:21
 * @since 1.0
 */
public class NIOServer {

  public static void main(String[] args) throws IOException {
    // 创建选择器
    Selector selector = Selector.open();
    // 将通道注册到选择器上
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    // 绑定连接
    serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8888));
    //获取到达的事件
    while (selector.select() > 0) {
      final Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
      while (keyIterator.hasNext()) {
        final SelectionKey key = keyIterator.next();
        if (key.isAcceptable()) {
          // 接收事件就绪
          SocketChannel channel = serverSocketChannel.accept();
          channel.configureBlocking(false);
          channel.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
          // 读事件就绪
          SocketChannel channel = (SocketChannel) key.channel();
          System.out.println(readDataFromSocketChannel(channel));
          channel.close();
        }
        keyIterator.remove();
      }
    }
  }

  private static String readDataFromSocketChannel(SocketChannel socketChannel) throws IOException {
    StringBuilder data = new StringBuilder();
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    while (socketChannel.read(buffer) > 0) {
      buffer.flip();
      int limit = buffer.limit();
      char[] dst = new char[limit];
      for (int i = 0; i < limit; i++) {
        dst[i] = (char) buffer.get(i);
      }
      data.append(dst);
      buffer.clear();
    }
    return data.toString();
  }
}
