package com.example.xxumt.aspect;

import com.example.xxumt.service.IJdkProxyService;
import com.example.xxumt.service.impl.CglibProxyServiceImpl;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/3/7 17:14
 * @since 1.0
 */
public class LogAspectTest {

  @Test
  public void jdkProxyAspectTest(){
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example.xxumt");
    IJdkProxyService service = context.getBean(IJdkProxyService.class);
    service.doMethod1();
    service.doMethod2();
    try {
      service.doMethod3();
    } catch (Exception e) {
      //            e.printStackTrace();
    }
  }

  @Test
  public void cglibProxyAspectTest() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example.xxumt");
    CglibProxyServiceImpl service = context.getBean(CglibProxyServiceImpl.class);
    service.doMethod1();
    service.doMethod2();
    try {
      service.doMethod3();
    } catch (Exception e) {
      //            e.printStackTrace();
    }
  }
}
