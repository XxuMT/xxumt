package com.example.xxumt.bean;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring Bean生命周期案例
 *
 * Bean实现Bean级生命周期接口方法
 * @author mengting.xu@ucarinc.com
 * @date 2024/3/11 16:18
 * @since 1.0
 */
@SuppressWarnings("ALL")
@Slf4j
@ToString
public class UserBean implements BeanFactoryAware, BeanNameAware, ApplicationContextAware, InitializingBean, DisposableBean {

    private String name;

    private int age;

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    private String beanName;

    public UserBean() {
        log.info("execute UserBean#new UserBean()");
    }
    public void setName(String name) {
        log.info("execute UserBean#setName({})", name);
        this.name = name;
    }

    public void setAge(int age) {
        log.info("execute UserBean#setAge({})", age);
        this.age = age;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("execute BeanFactoryAware#setBeanFactory");
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        log.info("execute BeanNameAware#setBeanName");
        this.beanName = name;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("execute ApplicationContextAware#setApplicationContext");
        this.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        log.info("execute DisposableBean#destory");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("execute InitializingBean#afterPropertiesSet");
    }

    public void doInit() {
        log.info("execute UserBean#doInit");
    }

    public void doDestroy() {
        log.info("execute UserBean#doDestroy");
    }
}
