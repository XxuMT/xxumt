package com.example.xxumt.http;

import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * jdk11新特性
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/9/18 17:14
 * @since 1.0
 */
public class HttpClientTest {
  @Test
  public void test() {
    var client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://www.baidu.com")).build();
    client
        .sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenAccept(System.out::println)
        .join();
  }
}
