package com.luomor.pcsms.solon.config;

import cn.hutool.core.util.ObjectUtil;
import com.luomor.pcsms.api.SmsBlend;
import com.luomor.pcsms.api.universal.SupplierConfig;
import com.luomor.pcsms.comm.constant.Constant;
import com.luomor.pcsms.comm.enums.ConfigType;
import com.luomor.pcsms.core.datainterface.SmsReadConfig;
import com.luomor.pcsms.provider.config.SmsConfig;
import com.luomor.pcsms.provider.factory.BaseProviderFactory;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Condition;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.AppContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * smsConfig 参数意义为确保注入时 smsConfig 已经存在
 */
@Configuration
public class SupplierConfigure {
    @Inject
    AppContext context;

    private <T> T injectObj(String prefix, T obj) {
        //@Inject 只支持在字段、参数、类型上注入
        context.cfg().getProp(prefix).bindTo(obj);
        return obj;
    }

    @Bean("blends")
    public Map<String, Map<String, Object>> blends() {
        //确保能产生（给下面用）//springboot 就算没产生，也会给个默认
        return context.cfg().getProp("sms.blends").bindTo(new LinkedHashMap<>());
    }

    @Bean
    @Condition(onBean = SmsConfig.class)
    public List<BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig>> factoryList(
            @Inject("blends") Map<String, Map<String, Object>> blends,
            SmsConfig smsConfig) throws Exception {
        //注入自定义实现工厂
        List<BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig>> factoryList = new ArrayList<>();
        if (ConfigType.YAML.equals(smsConfig.getConfigType())) {
            for (String configId : blends.keySet()) {
                Map<String, Object> configMap = blends.get(configId);
                Object factoryPath = configMap.get(Constant.FACTORY_PATH);
                if (ObjectUtil.isNotEmpty(factoryPath)) {
                    //反射创建实例
                    Class<BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig>> newClass = (Class<BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig>>) Class.forName(factoryPath.toString());
                    BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig> factory = newClass.newInstance();
                    factoryList.add(factory);
                }
            }
        }
        return factoryList;
    }


    @Bean
    public SmsBlendsInitializer smsBlendsInitializer(List<BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig>> factoryList,
                                                     SmsConfig smsConfig,
                                                     @Inject("blends") Map<String, Map<String, Object>> blends,
                                                     List<SmsReadConfig> extendsSmsConfigs) {
        return new SmsBlendsInitializer(factoryList, smsConfig, blends, extendsSmsConfigs);
    }
}
