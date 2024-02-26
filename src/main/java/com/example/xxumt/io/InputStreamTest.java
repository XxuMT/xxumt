package com.example.xxumt.io;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/2/2 14:43
 * @since 1.0
 */
public class InputStreamTest {

    private InputStream inputStream;
    private FileReader fileReader;
    private static final String CONTENT = "hello world";

    @Before
    public void setUp() throws Exception{
        this.inputStream = InputStreamTest.class.getResourceAsStream("/input.txt");
        this.fileReader = new FileReader("/Users/xxumt/ucar/project/xxumt/src/main/resources/input.txt");
    }

    @Test
    public void testReadAllBytes() throws Exception {
        // jdk1.9后增加方法
        final String content = new String(this.inputStream.readAllBytes());
        assert CONTENT.equals(content);
    }

    @Test
    public void testReadFileContent() throws Exception{
        /*
        URL url = new URL("http://www.baidu.com");
        InputStream is = url.openStream();
        InputStreamReader reader = new InputStreamReader(is, "utf-8");
        */

        BufferedReader bufferedReader = new BufferedReader(this.fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        bufferedReader.close();
    }

    @Test
    public void testReadNBytes() throws IOException {
        final byte[] data = new byte[5];
        this.inputStream.readNBytes(data, 0, 5);
        assert "hello".equals(new String(data));
    }

    @Test
    public void testTransferTo() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.inputStream.transferTo(outputStream);
        assert CONTENT.equals(outputStream.toString());
    }
}
