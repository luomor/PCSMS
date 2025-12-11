package com.luomor.pcsms.danmi.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.luomor.pcsms.comm.constant.Constant;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.config.BaseConfig;

/**
 * <p>类名: DanMiConfig
 * <p>说明： 旦米短信差异配置
 *
 * @author Peter
 * 2025/6/23  17:06
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class DanMiConfig extends BaseConfig {

    /**
     * 请求地址
     */
    private String host = Constant.HTTPS_PREFIX + "openapi.danmi.com/";

    /**
     * 请求方法
     * 短信发送 distributor/sendSMS
     * 短信余额查询 distributor/user/query
     * 语音验证码发送 voice/voiceCode
     * 语音通知文件发送 voice/voiceNotify
     * 语音模板通知发送 voice/voiceTemplate
     */
    private String action = "distributor/sendSMS";

    /**
     * 获取供应商
     *
     * @since 3.0.0
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.DAN_MI;
    }
}
