package com.luomor.oa.core.byteTalk.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.luomor.oa.comm.enums.OaType;
import com.luomor.oa.core.provider.config.OaBaseConfig;

@Data
@EqualsAndHashCode(callSuper = true)
public class ByteTalkConfig extends OaBaseConfig {

    private final String requestUrl = OaType.BYTE_TALK.getUrl();

    @Override
    public String getSupplier() {
        return OaType.BYTE_TALK.getType();
    }
}