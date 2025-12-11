package com.luomor.oa.core.dingTalk.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.oa.comm.content.OaContent;
import com.luomor.oa.core.dingTalk.service.DingTalkOaImpl;
import com.luomor.oa.core.provider.factory.OaAbstractProviderFactory;


/**
 * 钉钉通知对象建造
 * @author Peter
 * 2023-10-22 21:00
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DingTalkFactory extends OaAbstractProviderFactory<DingTalkOaImpl, DingTalkConfig> {
    private static final DingTalkFactory INSTANCE = new DingTalkFactory();

    /**
     * 建造一个钉钉通知实现
     */
    @Override
    public DingTalkOaImpl createSmsOa(DingTalkConfig dingTalkConfig) {
        return new DingTalkOaImpl(dingTalkConfig);
    }


    @Override
    public String getSupplier() {
        return OaContent.DING_TALK;
    }

    /**
     * 获取建造者实例
     *
     * @return 建造者实例
     */
    public static DingTalkFactory instance() {
        return INSTANCE;
    }

}
