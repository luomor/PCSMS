package com.luomor.oa.core.config;

import lombok.Data;
import com.luomor.oa.comm.task.delayed.DelayedTime;
import com.luomor.oa.core.provider.config.OaConfig;
import com.luomor.oa.core.provider.factory.OaBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.util.concurrent.Executor;

/**
 * @author Peter
 * 2023-11-01 18:05
 */
@Data
public class OaMainConfig {

    @Bean
    @ConfigurationProperties(prefix = "sms-oa")     //指定配置文件注入属性前缀
    @ConditionalOnProperty(prefix = "sms-oa", name = "config-type", havingValue = "yaml")
    protected OaConfig oaConfig() {
        return OaBeanFactory.getSmsConfig();
    }

    /**
     * 注入一个定时器
     */
    @Bean("oaDelayedTime")
    @Lazy
    protected DelayedTime delayedTime() {
        return OaBeanFactory.getDelayedTime();
    }

    /**
     * 注入线程池
     */
    @Bean("oaExecutor")
    protected Executor taskExecutor(OaConfig config) {
        return OaBeanFactory.setExecutor(config);
    }

}
