package com.luomor.pcsms.example.zhangjun;

import lombok.NoArgsConstructor;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;

/**
 *
 * <p> 掌骏短信
 *
 * @author Peter
 * 2025/10/31  14:54
 **/
@NoArgsConstructor
public class ZhangJunFactory extends AbstractProviderFactory<ZhangJunSmsImpl, ZhangJunConfig> {

    @Override
    public ZhangJunSmsImpl createSms(ZhangJunConfig ZhangJunConfig) {
        return new ZhangJunSmsImpl(ZhangJunConfig);
    }

    @Override
    public String getSupplier() {
        return "zhangjun";
    }

}
