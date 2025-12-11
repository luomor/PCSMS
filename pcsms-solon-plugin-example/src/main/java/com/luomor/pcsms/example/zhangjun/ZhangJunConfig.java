package com.luomor.pcsms.example.zhangjun;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.luomor.pcsms.provider.config.BaseConfig;

/**
 * @author Peter
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ZhangJunConfig extends BaseConfig {
    private String appId;
    private String sid;
    private String url;

    @Override
    public String getSupplier() {
        return "zhangjun";
    }

}
