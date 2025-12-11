package com.luomor.pcsms.jdcloud.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.config.BaseConfig;

/**
 * 京东云短信配置属性
 *
 * @author Peter
 * @since 2025/4/10 20:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JdCloudConfig extends BaseConfig {

    /**
     * 地域信息
     */
    private String region = "cn-north-1";

    /**
     * 获取供应商
     *
     * @since 3.0.0
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.JDCLOUD;
    }

}
