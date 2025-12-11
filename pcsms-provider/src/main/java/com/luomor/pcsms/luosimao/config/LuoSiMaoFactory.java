package com.luomor.pcsms.luosimao.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.luosimao.service.LuoSiMaoSmsImpl;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;

/**
 * <p>类名: LuoSiMaoFactory
 *
 * @author Peter
 * 2025/6/21  23:59
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LuoSiMaoFactory extends AbstractProviderFactory<LuoSiMaoSmsImpl, LuoSiMaoConfig> {

    private static final LuoSiMaoFactory INSTANCE = new LuoSiMaoFactory();

    /**
     * 获取建造者实例
     * @return 建造者实例
     */
    public static LuoSiMaoFactory instance() {
        return INSTANCE;
    }

    /**
     * <p> 建造一个短信实现对像
     *
     * @author Peter
     */
    @Override
    public LuoSiMaoSmsImpl createSms(LuoSiMaoConfig config) {
        return new LuoSiMaoSmsImpl(config);
    }

    /**
     * 获取供应商
     * @return 供应商
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.LUO_SI_MAO;
    }

}
