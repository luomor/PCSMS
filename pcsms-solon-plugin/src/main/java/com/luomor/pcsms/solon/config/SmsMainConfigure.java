package com.luomor.pcsms.solon.config;

import lombok.extern.slf4j.Slf4j;
import com.luomor.pcsms.comm.constant.Constant;
import com.luomor.pcsms.provider.config.SmsBanner;
import com.luomor.pcsms.provider.config.SmsConfig;
import com.luomor.pcsms.provider.factory.BeanFactory;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Init;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.AppContext;

@Slf4j
@Configuration
public class SmsMainConfigure {
    @Inject
    AppContext context;

    @Bean
    public SmsConfig smsConfig() {
        return context.cfg().getProp("sms")
                .bindTo(BeanFactory.getSmsConfig());
    }

    //是在 solon 容器扫描完成之后执行的
    @Init
    public void init() {
        //打印banner
        if (BeanFactory.getSmsConfig().getIsPrint()) {
            SmsBanner.PrintBanner(Constant.VERSION);
        }
    }
}
