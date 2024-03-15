package com.example.xxumt.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

/**
 * 客户端
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/2/29 15:58
 * @since 1.0
 */
public class NoBlockClient {

  public static void main(String[] args) throws IOException {
    // 获取通道
    SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(6666));
    // 切换成非阻塞模式
    socketChannel.configureBlocking(false);
    // 获取选择器
    Selector selector = Selector.open();
    // 将通道注册到选择器上
    socketChannel.register(selector, SelectionKey.OP_READ);
    // 发送图片给服务端
    FileChannel fileChannel =
        FileChannel.open(Paths.get("/Users/xxumt/Downloads/IMG_4352.PNG"), StandardOpenOption.READ);
    // 通过buffer与数据打交道
    ByteBuffer buffer = ByteBuffer.allocate(1024);

    // 读取本地文件，发送到服务器
    while (fileChannel.read(buffer) > 0) {
      buffer.flip();
      socketChannel.write(buffer);
      buffer.clear();
    }

    while (selector.select() > 0) {
      Iterator<SelectionKey> iterable = selector.selectedKeys().iterator();
      while (iterable.hasNext()) {
        SelectionKey key = iterable.next();
        if (key.isReadable()) {
          SocketChannel channel = (SocketChannel) key.channel();

          ByteBuffer responseBuffer = ByteBuffer.allocate(1024);

          // 获取服务端要返回响应的数据给客户端，客户端在此接收
          int readBytes = channel.read(responseBuffer);

          if (readBytes > 0) {
            responseBuffer.flip();
            System.out.println(new String(responseBuffer.array(), 0, readBytes));
          }
        }
      }
      iterable.remove();
    }
  }
}
