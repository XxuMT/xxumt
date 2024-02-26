package com.example.xxumt.io.bio;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端 单个线程
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/4/20 10:59
 * @since 1.0
 */
@Slf4j
public class SocketServer1 {
    static {
        BasicConfigurator.configure();
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(83);

        try {
            while (true) {
                Socket socket = serverSocket.accept();

                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                Integer sourcePort = socket.getPort();
                int maxLen = 2048;
                byte[] contextBytes = new byte[maxLen];
                int realLen = in.read(contextBytes, 0, maxLen);
                String msg = new String(contextBytes, 0, realLen);

                log.info("服务端接收到来自于端口：" + sourcePort + "的信息：" + msg);

                out.write("回发响应信息！".getBytes());

                out.close();
                in.close();
                socket.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (null != serverSocket) {
                serverSocket.close();
            }
        }
    }
}
