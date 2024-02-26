package com.example.xxumt.io.nio;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 文件读写、映射和操作的通道，并发环境下线程安全 基于 FileInputStream、FileOutputStream 或者 RandomAccessFile 的 getChannel()
 * 方法可以创建并打开一个文件通道
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/8/11 11:24
 * @since 1.0
 */
public class FileChannelTest {

  private static final String CONTENT = "Zero copy implement by FileChannel, XJF & XMT";
  private static final String SOURCE_FILE = "/source.txt";
  private static final String TARGET_FILE = "/target.txt";

  @Before
  public void setUp() {
    Path source = Paths.get(getClassPath(SOURCE_FILE));
    byte[] bytes = CONTENT.getBytes(StandardCharsets.UTF_8);
    try (FileChannel fromChannel =
        FileChannel.open(
            source,
            StandardOpenOption.READ,
            StandardOpenOption.WRITE,
            StandardOpenOption.TRUNCATE_EXISTING)) {
      fromChannel.write(ByteBuffer.wrap(bytes));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String getClassPath(String sourceFile) {
    return getClass().getResource(SOURCE_FILE).getPath();
  }

  @Test
  public void transferFrom() {
    try (FileChannel fromChannel =
            new RandomAccessFile(getClassPath(SOURCE_FILE), "rw").getChannel();
        FileChannel toChannel =
            new RandomAccessFile(getClassPath(TARGET_FILE), "rw").getChannel()) {
      long position = 0L;
      long offset = fromChannel.size();
      fromChannel.transferTo(position, offset, toChannel);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void transferTo() {
    try (FileChannel fromChannel =
            new RandomAccessFile(getClassPath(SOURCE_FILE), "rw").getChannel();
        FileChannel toChannel =
            new RandomAccessFile(getClassPath(TARGET_FILE), "rw").getChannel()) {
      long position = 0;
      long offset = fromChannel.size();
      toChannel.transferFrom(fromChannel, position, offset);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
