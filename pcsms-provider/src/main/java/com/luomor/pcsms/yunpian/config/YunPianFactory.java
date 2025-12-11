package com.luomor.pcsms.yunpian.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;
import com.luomor.pcsms.yunpian.service.YunPianSmsImpl;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YunPianFactory extends AbstractProviderFactory<YunPianSmsImpl, YunpianConfig> {

    private static final YunPianFactory INSTANCE = new YunPianFactory();

    /**
     * 获取建造者实例
     * @return 建造者实例
     */
    public static YunPianFactory instance() {
        return INSTANCE;
    }

    /**
     * 建造一个云片短信实现
     */
    @Override
    public YunPianSmsImpl createSms(YunpianConfig yunpianConfig){
        return new YunPianSmsImpl(yunpianConfig);
    }

    /**
     * 获取供应商
     *
     * @since 3.0.0
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.YUNPIAN;
    }

}
