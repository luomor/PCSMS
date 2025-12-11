package com.luomor.pcsms.api.proxy.aware;

import java.util.Map;

/**
 * 厂商配置感知接口
 *
 * @author Peter
 * @since 2025/10/27 13:03
 */
public interface SmsBlendConfigAware {
    void setSmsBlendsConfig(Map<String, Map<String, Object>> blends);
}
