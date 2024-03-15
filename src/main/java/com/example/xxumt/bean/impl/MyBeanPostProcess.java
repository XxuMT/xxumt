package com.example.xxumt.bean.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/3/15 15:06
 * @since 1.0
 */
@Slf4j
@Component
@SuppressWarnings("ALL")
public class MyBeanPostProcess implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    log.info("execution BeanPostProcessor#postProcessBeforeInitialization for {}", beanName);
    return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    log.info("execution BeanPostProcessor#postProcessAfterInitialization for {}", beanName);
    return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
  }
}
