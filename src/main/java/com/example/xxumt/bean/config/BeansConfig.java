package com.example.xxumt.bean.config;

import com.example.xxumt.bean.UserBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过Java配置方式初始化Bean
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/3/15 15:10
 * @since 1.0
 */
@Configuration
public class BeansConfig {

    @Bean(name = "userBean", initMethod = "doInit", destroyMethod = "doDestroy")
    public UserBean create() {
        UserBean userBean = new UserBean();
        userBean.setName("XMT");
        userBean.setAge(26);
        return userBean;
    }
}
