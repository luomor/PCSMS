package com.luomor.pcsms.ctyun.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.luomor.pcsms.comm.constant.Constant;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.config.BaseConfig;

/**
 * <p>类名: CtyunConfig
 * <p>说明： 天翼云短信差异配置
 *
 * @author Peter
 * 2025/5/12  15:06
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class CtyunConfig extends BaseConfig {

    /**
     * 模板变量名称
     */
    private String templateName;

    /**
     * 请求地址
     */
    private String requestUrl = Constant.HTTPS_PREFIX + "sms-global.ctapi.ctyun.cn/sms/api/v1";

    /**
     * 接口名称
     */
    private String action = "SendSms";

    /**
     * 获取供应商
     *
     * @since 3.0.0
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.CTYUN;
    }
}
