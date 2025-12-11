package com.luomor.pcsms.aliyun.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.config.BaseConfig;

/**
 * @author Peter
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AlibabaConfig extends BaseConfig {

    /**
     * 模板变量名称
     */
    private String templateName;

    /**
     * 请求地址
     */
    private String requestUrl = "dysmsapi.aliyuncs.com";

    /**
     * 接口名称
     */
    private String action = "SendSms";

    /**
     * 接口版本号
     */
    private String version = "2017-05-25";

    /**
     * 地域信息默认为 cn-hangzhou
     */
    private String regionId = "cn-hangzhou";

    /**
     * 获取供应商
     *
     * @since 3.0.0
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.ALIBABA;
    }

}
