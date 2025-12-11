package com.luomor.oa.core.byteTalk.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.oa.comm.content.OaContent;
import com.luomor.oa.core.byteTalk.service.ByteTalkOaImpl;
import com.luomor.oa.core.provider.factory.OaAbstractProviderFactory;

/**
 * 飞书通知对象建造
 * @author Peter
 * 2023-10-22 21:00
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ByteTalkFactory extends OaAbstractProviderFactory<ByteTalkOaImpl, ByteTalkConfig> {
    private static final ByteTalkFactory INSTANCE = new ByteTalkFactory();

    /**
     * 建造一个飞书通知对象实现
     */
    @Override
    public ByteTalkOaImpl createSmsOa(ByteTalkConfig byteTalkConfig) {
        return new ByteTalkOaImpl(byteTalkConfig);
    }

    @Override
    public String getSupplier() {
        return OaContent.BYTE_TALK;
    }

    /**
     * 获取建造者实例
     *
     * @return 建造者实例
     */
    public static ByteTalkFactory instance() {
        return INSTANCE;
    }

}
