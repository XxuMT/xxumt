package com.example.xxumt.io.nio;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 套接字实例Client
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/2/28 17:04
 * @since 1.0
 */
public class NIOClient {
  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("127.0.0.1", 8888);
    OutputStream out = socket.getOutputStream();
    String str = "hello world";
    out.write(str.getBytes(StandardCharsets.UTF_8));
    out.close();
  }
}
