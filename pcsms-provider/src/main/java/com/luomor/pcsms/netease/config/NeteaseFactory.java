package com.luomor.pcsms.netease.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.netease.service.NeteaseSmsImpl;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;

/**
 * NeteaseSmsConfig
 * <p> 网易云信短信
 *
 * @author Peter
 * 2023-05-30
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NeteaseFactory extends AbstractProviderFactory<NeteaseSmsImpl, NeteaseConfig> {

    private static final NeteaseFactory INSTANCE = new NeteaseFactory();

    /**
     * 获取建造者实例
     * @return 建造者实例
     */
    public static NeteaseFactory instance() {
        return INSTANCE;
    }

    /**
     * 建造一个网易云的短信实现
     */
    @Override
    public NeteaseSmsImpl createSms(NeteaseConfig neteaseConfig) {
        return new NeteaseSmsImpl(neteaseConfig);
    }

    /**
     * 获取供应商
     *
     * @since 3.0.0
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.NETEASE;
    }

}
