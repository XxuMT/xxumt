package com.example.xxumt.service.impl;

import com.example.xxumt.service.IJdkProxyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 非接口使用Cglib代理
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/3/7 16:24
 * @since 1.0
 */
@Service
public class CglibProxyServiceImpl {
  public void doMethod1() {
    System.out.println("CglibProxyServiceImpl.doMethod1()");
  }

  @Transactional
  public String doMethod2() {
    System.out.println("CglibProxyServiceImpl.doMethod2()");
    return "hello world!";
  }

  public String doMethod3() throws Exception {
    System.out.println("CglibProxyServiceImpl.doMethod3()");
    throw new Exception("happened exception");
  }
}
