package com.luomor.pcsms.baidu.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.baidu.service.BaiduSmsImpl;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;

/**
 * <p>类名: BaiduFactory
 * <p>说明：百度智能云 sms
 *
 * @author Peter
 * 2025/4/25  13:40
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaiduFactory extends AbstractProviderFactory<BaiduSmsImpl, BaiduConfig> {

    private static final BaiduFactory INSTANCE = new BaiduFactory();

    /**
     * 获取建造者实例
     * @return 建造者实例
     */
    public static BaiduFactory instance() {
        return INSTANCE;
    }

    /**
     * createSms
     * <p> 建造一个短信实现对像
     *
     * @author Peter
     */
    @Override
    public BaiduSmsImpl createSms(BaiduConfig baiduConfig) {
        return new BaiduSmsImpl(baiduConfig);
    }

    /**
     * 获取供应商
     * @return 供应商
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.BAIDU;
    }

}
