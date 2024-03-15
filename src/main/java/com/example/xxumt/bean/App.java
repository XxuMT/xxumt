package com.example.xxumt.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/3/15 15:13
 * @since 1.0
 */
@Slf4j
public class App {

  public static void main(String[] args) {
    log.info("Init application context");
      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example.xxumt.bean");
      UserBean userBean = (UserBean) context.getBean("userBean");

      log.info(userBean.toString());
      log.info("Shutdown application context");
      context.registerShutdownHook();
  }
}
