package com.luomor.pcsms.dingzhong.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.dingzhong.service.DingZhongSmsImpl;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;

/**
 * DingZhongFactory
 * <p> 鼎众对象建造者
 *
 * @author Peter
 * 2025/12/26  14:54
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DingZhongFactory extends AbstractProviderFactory<DingZhongSmsImpl, DingZhongConfig> {

    private static final DingZhongFactory INSTANCE = new DingZhongFactory();

    /**
     * 获取建造者实例
     * @return 建造者实例
     */
    public static DingZhongFactory instance() {
        return INSTANCE;
    }

    /**
     * 创建短信实现对象
     * @param dingZhongConfig 短信配置对象
     * @return 短信实现对象
     */
    @Override
    public DingZhongSmsImpl createSms(DingZhongConfig dingZhongConfig) {
        return new DingZhongSmsImpl(dingZhongConfig);
    }

    /**
     * 获取供应商
     * @return 供应商
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.DINGZHONG;
    }

}
