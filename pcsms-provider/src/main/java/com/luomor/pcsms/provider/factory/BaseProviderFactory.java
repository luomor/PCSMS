package com.luomor.pcsms.provider.factory;

import com.luomor.pcsms.api.SmsBlend;
import com.luomor.pcsms.api.universal.SupplierConfig;

/**
 * AlibabaSmsConfig
 * <p>短信对象建造者</p>
 * @param <S> 短信对象
 * @param <C> 短信配置对象
 */
public interface BaseProviderFactory<S extends SmsBlend, C extends SupplierConfig> {

    /**
     * 创建短信实现对象
     * @param c 短信配置对象
     * @return 短信实现对象
     */
    S createSms(C c);

    /**
     * 获取配置类
     * @return 配置类
     */
    Class<C> getConfigClass();

    /**
     * 获取供应商
     * @return 供应商
     */
    String getSupplier();

}
