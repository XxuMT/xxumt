package com.example.xxumt.io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/7/7 16:05
 * @since 1.0
 */
public class Handler implements Runnable {
  private final SocketChannel channel;
  private static volatile Selector selector;
  private SelectionKey key;
  private volatile ByteBuffer input = ByteBuffer.allocate(1024);
  private volatile ByteBuffer output = ByteBuffer.allocate(1024);

  public Handler(SocketChannel channel) throws IOException {
    this.channel = channel;
    // 设置客户端连接为非阻塞模式
    channel.configureBlocking(false);
    // 为客户端创建一个新的多路复用器
    selector = Selector.open();
    // 注册客户端channel的读事件
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

  /**
   * 解决JDK bug，防止Selector在没有任何事件到达时被意外触发 处理方式：新建一个Selector，讲当前Channel重新注册到该Selector上
   *
   * @return 选择器集合
   * @throws IOException 异常
   */
  private Set<SelectionKey> select() throws IOException {
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

  /**
   * 读取客户端发送的数据
   *
   * @param key 选择器
   * @throws IOException io异常
   */
  private void read(SelectionKey key) throws IOException {
    channel.read(input);
    if (input.position() == 0) {
      return;
    }

    input.flip();
    // 对读取的数据进行业务处理
    process();
    input.clear();
    // 读取完成后监听写入事件
    key.interestOps(SelectionKey.OP_WRITE);
  }

  /**
   * 写入数据
   *
   * @param key 选择器
   * @throws IOException io异常
   */
  private void write(SelectionKey key) throws IOException {
    output.flip();
    if (channel.isOpen()) {
      // 有写入事件时，将业务处理的结果写入到客户端Channel中
      channel.write(output);
      key.channel();
      channel.close();
      output.clear();
    }
  }

  /** 业务处理，并获取处理结果 也可将处理过程放入线程池，并用Future获取处理结果，最后写入客户端Channel，提高性能 */
  private void process() {
    byte[] bytes = new byte[input.remaining()];
    input.get(bytes);
    String message = new String(bytes, StandardCharsets.UTF_8);
    System.out.println("receive message from clinet: \n" + message);
    output.put("hello client".getBytes());
  }
}
