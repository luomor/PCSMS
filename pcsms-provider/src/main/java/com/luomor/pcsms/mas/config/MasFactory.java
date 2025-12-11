package com.luomor.pcsms.mas.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.mas.service.MasSmsImpl;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;

/**
 * <p>类名: MasFactory
 * <p>说明：中国移动 云MAS短信配置器
 *
 * @author Peter
 * 2025/4/22  13:40
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MasFactory extends AbstractProviderFactory<MasSmsImpl, MasConfig> {

    private static final MasFactory INSTANCE = new MasFactory();

    /**
     * 获取建造者实例
     * @return 建造者实例
     */
    public static MasFactory instance() {
        return INSTANCE;
    }

    /**
     * createSms
     * <p> 建造一个短信实现对像
     *
     * @author Peter
     */
    @Override
    public MasSmsImpl createSms(MasConfig masConfig) {
        return new MasSmsImpl(masConfig);
    }

    /**
     * 获取供应商
     * @return 供应商
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.MAS;
    }

}
