package com.luomor.oa.core.weTalk.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.luomor.oa.comm.enums.OaType;
import com.luomor.oa.core.provider.config.OaBaseConfig;

@Data
@EqualsAndHashCode(callSuper = true)
public class WeTalkConfig extends OaBaseConfig {

    private final String requestUrl = OaType.WE_TALK.getUrl();

    @Override
    public String getSupplier() {
        return OaType.WE_TALK.getType();
    }
}