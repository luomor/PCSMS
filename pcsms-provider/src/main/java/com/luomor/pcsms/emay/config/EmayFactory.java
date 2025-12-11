package com.luomor.pcsms.emay.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.emay.service.EmaySmsImpl;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;

/**
 * EmaySmsConfig
 * <p> Emay短信对象建造
 *
 * @author Peter
 * @date 2025/04/11 12:00
 * */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmayFactory extends AbstractProviderFactory<EmaySmsImpl, EmayConfig> {

    private static final EmayFactory INSTANCE = new EmayFactory();

    /**
     * 获取建造者实例
     * @return 建造者实例
     */
    public static EmayFactory instance() {
        return INSTANCE;
    }

    /**
     * 创建亿美软通短信实现对象
     * @param emayConfig 短信配置对象
     * @return 短信实现对象
     */
    @Override
    public EmaySmsImpl createSms(EmayConfig emayConfig) {
        return new EmaySmsImpl(emayConfig);
    }

    /**
     * 获取供应商
     * @return 供应商
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.EMAY;
    }

}
