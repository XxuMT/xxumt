package com.example.xxumt.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

/**
 * 服务端
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/2/29 14:22
 * @since 1.0
 */
public class NoBlockServer {
  public static void main(String[] args) throws Exception {
    // 获取服务端通道
    ServerSocketChannel serverChannel = ServerSocketChannel.open();
    // 设置成非阻塞模式
    serverChannel.configureBlocking(false);
    // 绑定连接
    serverChannel.bind(new InetSocketAddress(6666));
    // 获取选择器
    Selector selector = Selector.open();
    // 将通道注册到选择器上，指定接收"监听通道"事件
    serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    while (selector.select() > 0) {
      // 获取当前选择器所有注册的选择键
      Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
      // 获取已就绪的事件
      while (iterator.hasNext()) {
        // 轮询获取选择器上已就绪事件
        SelectionKey key = iterator.next();
        // 接收事件就绪
        if (key.isAcceptable()) {
          // 获取客户端连接
          SocketChannel client = serverChannel.accept();
          // 切换成非阻塞模式
          client.configureBlocking(false);
          // 注册到选择器上 --> 拿到客户端连接为了读取通道的数据（监听读事件就绪）
          client.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
          // 读事件就绪
          // 获取当前选择器读就绪通道
          SocketChannel client = (SocketChannel) key.channel();
          // 读取数据
          ByteBuffer buffer = ByteBuffer.allocate(1024);
          // 得到文件通道，将客户端传递过来的图片写到本地项目下（写模式，没有则创建）
          FileChannel outChannel =
              FileChannel.open(
                  Paths.get("2.png"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

          while (client.read(buffer) > 0) {
            // 切换成读模式
            buffer.flip();

            outChannel.write(buffer);
            // 读完切换成写模式，让管道继续读取文件的数据
            buffer.clear();
          }
        }
      }
      // 已处理过的事件，需要移除取消
      iterator.remove();
    }
  }
}
