package com.example.xxumt.io.nio;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static java.nio.channels.FileChannel.MapMode.READ_WRITE;

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
  private static final String FILE = "/mmap.txt";

//  @Before
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
    return Objects.requireNonNull(getClass().getResource(sourceFile)).getPath();
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

  @Test
  public void writeToFileByMappedByteBuffer() {
    Path path = Paths.get(getClassPath(FILE));
    byte[] bytes = CONTENT.getBytes(StandardCharsets.UTF_8);
    try (FileChannel fileChannel =
        FileChannel.open(
            path,
            StandardOpenOption.READ,
            StandardOpenOption.WRITE,
            StandardOpenOption.TRUNCATE_EXISTING)) {
      MappedByteBuffer buffer = fileChannel.map(READ_WRITE, 0, bytes.length);
      if (null != buffer) {
        buffer.put(bytes);
        buffer.force();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void readFromFileByMapperByteBuffer() {
    Path path = Paths.get(getClassPath(FILE));
    int len = CONTENT.getBytes(StandardCharsets.UTF_8).length;
    try(FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)){
      MappedByteBuffer buffer = fileChannel.map(READ_ONLY, 0 , len);
      if (null != buffer) {
        byte[] bytes = new byte[len];
        buffer.get(bytes);
        String content = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(content);
        assert CONTENT.equals(content);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
