package com.luomor.pcsms.api.proxy.aware;


/**
 * 系统配置感知接口
 *
 * @author Peter
 * @since 2025/10/27 13:03
 */
public interface SmsConfigAware {
    void setSmsConfig(Object config);
}
