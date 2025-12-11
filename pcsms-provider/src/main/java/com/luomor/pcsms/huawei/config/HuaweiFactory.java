package com.luomor.pcsms.huawei.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.huawei.service.HuaweiSmsImpl;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;

/**
 * HuaweiSmsConfig
 * <p> 华为短信对象建造
 *
 * @author Peter
 * 2025/4/8  15:27
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HuaweiFactory extends AbstractProviderFactory<HuaweiSmsImpl, HuaweiConfig> {

    private static final HuaweiFactory INSTANCE = new HuaweiFactory();

    /**
     * 获取建造者实例
     * @return 建造者实例
     */
    public static HuaweiFactory instance() {
        return INSTANCE;
    }

    /** 建造一个华为短信实现*/
    @Override
    public HuaweiSmsImpl createSms(HuaweiConfig huaweiConfig) {
        return new HuaweiSmsImpl(huaweiConfig);
    }

    /**
     * 获取供应商
     * @return 供应商
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.HUAWEI;
    }

}
