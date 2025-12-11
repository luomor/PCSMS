package com.luomor.pcsms.aliyun.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import com.luomor.pcsms.aliyun.config.AlibabaConfig;
import com.luomor.pcsms.aliyun.utils.AliyunUtils;
import com.luomor.pcsms.api.entity.SmsResponse;
import com.luomor.pcsms.api.utils.SmsRespUtils;
import com.luomor.pcsms.comm.constant.Constant;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.comm.delayedTime.DelayedTime;
import com.luomor.pcsms.comm.exception.SmsBlendException;
import com.luomor.pcsms.comm.utils.SmsUtils;
import com.luomor.pcsms.provider.service.AbstractSmsBlend;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * <p>类名: AlibabaSmsImpl
 * <p>说明：  阿里云短信实现
 *
 * @author Peter
 * 2025/3/26  17:16
 **/
@Slf4j
public class AlibabaSmsImpl extends AbstractSmsBlend<AlibabaConfig> {

    private int retry = 0;

    /**
     * AlibabaSmsImpl
     * <p>构造器，用于构造短信实现模块
     *
     * @author Peter
     */
    public AlibabaSmsImpl(AlibabaConfig config, Executor pool, DelayedTime delayedTime) {
        super(config, pool, delayedTime);
    }

    /**
     * AlibabaSmsImpl
     * <p>构造器，用于构造短信实现模块
     */
    public AlibabaSmsImpl(AlibabaConfig config) {
        super(config);
    }

    @Override
    public String getSupplier() {
        return SupplierConstant.ALIBABA;
    }

    @Override
    public SmsResponse sendMessage(String phone, String message) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>(1);
        map.put(getConfig().getTemplateName(), message);
        return sendMessage(phone, getConfig().getTemplateId(), map);
    }

    @Override
    public SmsResponse sendMessage(String phone, LinkedHashMap<String, String> messages) {
        if (Objects.isNull(messages)){
            messages = new LinkedHashMap<>();
        }
        return sendMessage(phone, getConfig().getTemplateId(), messages);
    }

    @Override
    public SmsResponse sendMessage(String phone, String templateId, LinkedHashMap<String, String> messages) {
        if (Objects.isNull(messages)){
            messages = new LinkedHashMap<>();
        }
        String messageStr = JSONUtil.toJsonStr(messages);
        return getSmsResponse(phone, messageStr, templateId);
    }

    @Override
    public SmsResponse massTexting(List<String> phones, String message) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(getConfig().getTemplateName(), message);
        return massTexting(phones, getConfig().getTemplateId(), map);
    }

    @Override
    public SmsResponse massTexting(List<String> phones, String templateId, LinkedHashMap<String, String> messages) {
        if (Objects.isNull(messages)){
            messages = new LinkedHashMap<>();
        }
        String messageStr = JSONUtil.toJsonStr(messages);
        return getSmsResponse(SmsUtils.addCodePrefixIfNot(phones), messageStr, templateId);
    }

    private SmsResponse getSmsResponse(String phone, String message, String templateId) {
        String requestUrl;
        String paramStr;
        try {
            requestUrl = AliyunUtils.generateSendSmsRequestUrl(getConfig(), message, phone, templateId);
            paramStr = AliyunUtils.generateParamBody(getConfig(), phone, message, templateId);
        } catch (Exception e) {
            log.error("aliyun send message error", e);
            throw new SmsBlendException(e.getMessage());
        }
        log.debug("requestUrl {}", requestUrl);

        Map<String, String> headers = MapUtil.newHashMap(1, true);
        headers.put(Constant.CONTENT_TYPE, Constant.APPLICATION_FROM_URLENCODED);
        SmsResponse smsResponse;
        try {
            smsResponse = getResponse(http.postJson(requestUrl, headers, paramStr));
        } catch (SmsBlendException e) {
            smsResponse = errorResp(e.message);
        }
        if (smsResponse.isSuccess() || retry >= getConfig().getMaxRetries()) {
            retry = 0;
            return smsResponse;
        }
        return requestRetry(phone, message, templateId);
    }

    private SmsResponse requestRetry(String phone, String message, String templateId) {
        http.safeSleep(getConfig().getRetryInterval());
        retry++;
        log.warn("短信第 {} 次重新发送", retry);
        return getSmsResponse(phone, message, templateId);
    }

    private SmsResponse getResponse(JSONObject resJson) {
        return SmsRespUtils.resp(resJson, "OK".equals(resJson.getStr("Code")), getConfigId());
    }

}