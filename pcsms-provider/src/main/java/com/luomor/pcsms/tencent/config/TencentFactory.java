package com.luomor.pcsms.tencent.config;

import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;
import com.luomor.pcsms.tencent.service.TencentSmsImpl;

/**
 * TencentSmsConfig
 * <p> 建造腾讯云短信
 *
 * @author Peter
 * 2025/4/8  16:05
 **/
public class TencentFactory extends AbstractProviderFactory<TencentSmsImpl, TencentConfig> {

    private static final TencentFactory INSTANCE = new TencentFactory();

    /**
     * 获取建造者实例
     * @return 建造者实例
     */
    public static TencentFactory instance() {
        return INSTANCE;
    }

    /**
     * 建造一个腾讯云的短信实现
     */
    @Override
    public TencentSmsImpl createSms(TencentConfig tencentConfig) {
        return new TencentSmsImpl(tencentConfig);
    }

    /**
     * 获取供应商
     *
     * @since 3.0.0
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.TENCENT;
    }

}
