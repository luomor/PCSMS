package com.luomor.pcsms.budingyun.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.budingyun.service.BudingV2SmsImpl;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;

/**
 * BudingV2Factory
 * <p> 布丁云V2短信对象建造
 *
 * @author Peter
 * @date 2025/03/21 12:00
 * */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BudingV2Factory extends AbstractProviderFactory<BudingV2SmsImpl, BudingV2Config> {
    private static final BudingV2Factory INSTANCE = new BudingV2Factory();

    public static BudingV2Factory instance() {
        return INSTANCE;
    }

    @Override
    public BudingV2SmsImpl createSms(BudingV2Config budingV2Config) {
        return new BudingV2SmsImpl(budingV2Config);
    }

    @Override
    public String getSupplier() {
        return SupplierConstant.BUDING_V2;
    }
}
