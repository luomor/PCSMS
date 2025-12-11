package com.luomor.pcsms.submail.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;
import com.luomor.pcsms.submail.service.SubMailSmsImpl;

/**
 * <p>类名: SubMailFactory
 *
 * @author Peter
 * 2025/6/22  13:59
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubMailFactory extends AbstractProviderFactory<SubMailSmsImpl, SubMailConfig> {

    private static final SubMailFactory INSTANCE = new SubMailFactory();

    /**
     * 获取建造者实例
     * @return 建造者实例
     */
    public static SubMailFactory instance() {
        return INSTANCE;
    }

    /**
     * <p> 建造一个短信实现对像
     *
     * @author Peter
     */
    @Override
    public SubMailSmsImpl createSms(SubMailConfig config) {
        return new SubMailSmsImpl(config);
    }

    /**
     * 获取供应商
     * @return 供应商
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.MY_SUBMAIL;
    }

}
