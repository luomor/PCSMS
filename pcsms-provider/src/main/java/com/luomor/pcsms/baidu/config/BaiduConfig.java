package com.luomor.pcsms.baidu.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.luomor.pcsms.comm.constant.Constant;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.config.BaseConfig;

/**
 * <p>类名: BaiduConfig
 * <p>说明：百度智能云 sms
 *
 * @author Peter
 * 2025/4/25  13:40
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class BaiduConfig extends BaseConfig {

    /**
     * 请求地址
     */
    private String host = Constant.HTTPS_PREFIX + "smsv3.bj.baidubce.com";

    /**
     * 接口名称
     */
    private String action = "/api/v3/sendSms";

    /**
     * 模板变量名称
     */
    private String templateName;

    /**
     * 用户自定义参数，格式为字符串，状态回调时会回传该值
     */
    private String custom;

    /**
     * 通道自定义扩展码
     */
    private String userExtId;

    /**
     * 获取供应商
     *
     * @since 3.0.0
     */
    @Override
    public String getSupplier() {
        return SupplierConstant.BAIDU;
    }
}
