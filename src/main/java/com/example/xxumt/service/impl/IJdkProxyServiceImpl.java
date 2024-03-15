package com.example.xxumt.service.impl;

import com.example.xxumt.service.IJdkProxyService;
import org.springframework.stereotype.Service;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/3/7 16:24
 * @since 1.0
 */
@Service("iJdkProxyService")
public class IJdkProxyServiceImpl implements IJdkProxyService {
  @Override
  public void doMethod1() {
    System.out.println("JdkProxyServiceImpl.doMethod1()");
  }

  @Override
  public String doMethod2() {

    System.out.println("JdkProxyServiceImpl.doMethod2()");
    return "hello world!";
  }

  @Override
  public String doMethod3() throws Exception {
    System.out.println("JdkProxyServiceImpl.doMethod3()");
    throw new Exception("happened exception");
  }
}
