package com.luomor.pcsms.danmi.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.danmi.service.DanMiSmsImpl;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;

/**
 * <p>类名: DanMiFactory
 *
 * @author Peter
 * 2025/6/23  17:06
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DanMiFactory extends AbstractProviderFactory<DanMiSmsImpl, DanMiConfig> {

    private static final DanMiFactory INSTANCE = new DanMiFactory();

    /**
     * 获取建造者实例
     * @return 建造者实例
     */
    public static DanMiFactory instance() {
        return INSTANCE;
    }

    /**
     * createSms
     * <p> 建造一个短信实现对像
     *
     * @author Peter
     */
    @Override
    public DanMiSmsImpl createSms(DanMiConfig config) {
        return new DanMiSmsImpl(config);
    }

    /**
     * 获取供应商
     * @return 供应商
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.DAN_MI;
    }

}
