package com.luomor.pcsms.starter.config;

import lombok.Data;
import com.luomor.pcsms.comm.constant.Constant;
import com.luomor.pcsms.provider.config.SmsBanner;
import com.luomor.pcsms.provider.config.SmsConfig;
import com.luomor.pcsms.provider.factory.BeanFactory;
import com.luomor.pcsms.starter.utils.ConfigUtils;
import com.luomor.pcsms.starter.utils.SmsSpringUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;


@Data
public class SmsMainConfig {

    @Bean
    public SmsSpringUtils smsSpringUtil(DefaultListableBeanFactory defaultListableBeanFactory){
        return new SmsSpringUtils(defaultListableBeanFactory);
    }

    @Bean
    @ConfigurationProperties(prefix = "sms")     //指定配置文件注入属性前缀
    protected SmsConfig smsConfig() {
        return BeanFactory.getSmsConfig();
    }

    /**
     * 注入一个配置文件读取工具
     */
    @Bean("smsConfigUtil")
    @Lazy
    protected ConfigUtils configUtil(Environment environment) {
        return new ConfigUtils(environment);
    }

    @EventListener
    void init(ContextRefreshedEvent event) {
        //打印banner
        if (BeanFactory.getSmsConfig().getIsPrint()) {
            SmsBanner.PrintBanner(Constant.VERSION);
        }
    }

}
