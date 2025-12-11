package com.luomor.pcsms.lianlu.config;

import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.lianlu.service.LianLuSmsImpl;
import com.luomor.pcsms.provider.factory.BaseProviderFactory;

/**
 * 联鹿短信
 * */
public class LianLuFactory implements BaseProviderFactory<LianLuSmsImpl, LianLuConfig> {
    private static final LianLuFactory INSTANCE = new LianLuFactory();

    private LianLuFactory() {
    }

    public static LianLuFactory instance() {
        return INSTANCE;
    }

    @Override
    public LianLuSmsImpl createSms(LianLuConfig lianLuConfig) {
        return new LianLuSmsImpl(lianLuConfig);
    }

    @Override
    public Class<LianLuConfig> getConfigClass() {
        return LianLuConfig.class;
    }

    @Override
    public String getSupplier() {
        return SupplierConstant.LIANLU;
    }
}
