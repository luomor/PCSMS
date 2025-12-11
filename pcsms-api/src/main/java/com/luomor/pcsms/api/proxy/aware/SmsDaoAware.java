package com.luomor.pcsms.api.proxy.aware;

import com.luomor.pcsms.api.dao.SmsDao;
/**
 * 缓存感知接口
 *
 * @author Peter
 * @since 2025/10/27 13:03
 */
public interface SmsDaoAware {
    void setSmsDao(SmsDao smsDao);
}
