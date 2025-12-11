package com.luomor.pcsms.provider.factory;

import cn.hutool.core.collection.CollUtil;
import com.luomor.pcsms.api.SmsBlend;
import com.luomor.pcsms.api.universal.SupplierConfig;
import com.luomor.pcsms.comm.exception.SmsBlendException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 供应商工厂持有者
 *
 * @author Peter
 * @since 3.0.0
 */
public class ProviderFactoryHolder {

    private static final Map<String, BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig>> FACTORIES = new ConcurrentHashMap<>();

    public static void registerFactory(BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig> factory) {
        if(factory == null) {
            throw new SmsBlendException("注册供应商工厂失败，工厂实例不能为空");
        }
        FACTORIES.put(factory.getSupplier(), factory);
    }

    public static void registerFactory(List<BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig>> factoryList) {
        if(CollUtil.isEmpty(factoryList)) {
            return;
        }
        for(BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig> factory : factoryList) {
            if(factory == null) {
                continue;
            }
            registerFactory(factory);
        }
    }

    public static BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig> requireForSupplier(String supplier) {
        return FACTORIES.getOrDefault(supplier, null);
    }

}
