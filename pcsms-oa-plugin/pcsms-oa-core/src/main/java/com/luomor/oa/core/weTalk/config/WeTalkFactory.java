package com.luomor.oa.core.weTalk.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.oa.comm.content.OaContent;
import com.luomor.oa.core.provider.factory.OaAbstractProviderFactory;
import com.luomor.oa.core.weTalk.service.WeTalkOaImpl;

/**
 * 微信通知对象建造
 * @author Peter
 * 2023-10-22 21:00
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeTalkFactory extends OaAbstractProviderFactory<WeTalkOaImpl, WeTalkConfig> {
    private static final WeTalkFactory INSTANCE = new WeTalkFactory();

    /**
     * 建造一个微信通知服务
     */
    @Override
    public WeTalkOaImpl createSmsOa(WeTalkConfig weTalkConfig) {
        return new WeTalkOaImpl(weTalkConfig);
    }

    @Override
    public String getSupplier() {
        return OaContent.WE_TALK;
    }

    /**
     * 获取建造者实例
     *
     * @return 建造者实例
     */
    public static WeTalkFactory instance() {
        return INSTANCE;
    }

}
