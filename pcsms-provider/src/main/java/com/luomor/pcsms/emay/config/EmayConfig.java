package com.luomor.pcsms.emay.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.config.BaseConfig;

/**
 * @author Peter
 * @date 2023-04-11 12:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmayConfig extends BaseConfig {

    /** APP接入地址*/
    private String requestUrl;

    /**
     * 获取供应商
     *
     * @since 3.0.0
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.EMAY;
    }

}
