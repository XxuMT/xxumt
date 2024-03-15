package com.example.xxumt.bean.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;
import reactor.util.annotation.Nullable;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/3/15 14:59
 * @since 1.0
 */
@Slf4j
@Component
@SuppressWarnings("ALL")
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

  @Override
  public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName)
      throws BeansException {
    log.info(
        "execution InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation for {}",
        beanName);
    return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInstantiation(
        beanClass, beanName);
  }

  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    log.info(
        "execution InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation for {}",
        beanName);
    return InstantiationAwareBeanPostProcessor.super.postProcessAfterInstantiation(bean, beanName);
  }

  @Override
  public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
      throws BeansException {
    log.info(
        "execution InstantiationAwareBeanPostProcessor#postProcessProperties for {}", beanName);
    return InstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
  }
}
