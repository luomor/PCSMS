package com.luomor.pcsms.chuanglan.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.chuanglan.service.ChuangLanSmsImpl;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;

/**
 * @author Peter
 * @Date: 2025/2/1 9:03 44
 * @描述: ChuangLanFactory
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChuangLanFactory extends AbstractProviderFactory<ChuangLanSmsImpl, ChuangLanConfig> {

    private static final ChuangLanFactory INSTANCE = new ChuangLanFactory();

    /**
     * 获取建造者实例
     *
     * @return 建造者实例
     */
    public static ChuangLanFactory instance() {
        return INSTANCE;
    }

    @Override
    public ChuangLanSmsImpl createSms(ChuangLanConfig chuangLanConfig) {
        return new ChuangLanSmsImpl(chuangLanConfig);
    }

    @Override
    public String getSupplier() {
        return SupplierConstant.CHUANGLAN;
    }
}