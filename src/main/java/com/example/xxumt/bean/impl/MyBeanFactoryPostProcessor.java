package com.example.xxumt.bean.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/3/15 14:57
 * @since 1.0
 */
@Slf4j
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    log.info("execution BeanFactoryPostProcessor#postProcessBeanFactory");
  }
}
