package com.luomor.pcsms.yixintong.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;
import com.luomor.pcsms.yixintong.service.YiXintongSmsImpl;

/**
 * <p>类名: YiXintongFactory
 * <p>说明：联通一信通平台短信对象建造
 *
 * @author Peter
 * @create 2024-07-30 17:10
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YiXintongFactory extends AbstractProviderFactory<YiXintongSmsImpl, YiXintongConfig> {

    private static final YiXintongFactory INSTANCE = new YiXintongFactory();

    /**
     * 获取建造者实例
     * @return 建造者实例
     */
    public static YiXintongFactory instance() {
        return INSTANCE;
    }

    /**
     * createSms
     * <p> 建造一个短信实现对像
     */
    @Override
    public YiXintongSmsImpl createSms(YiXintongConfig yiXintongConfig) {
        return new YiXintongSmsImpl(yiXintongConfig);
    }

    /**
     * 获取供应商
     * @return 供应商
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.YIXINTONG;
    }
}
