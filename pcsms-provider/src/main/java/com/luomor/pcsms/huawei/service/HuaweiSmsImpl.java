package com.luomor.pcsms.huawei.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.luomor.pcsms.api.entity.SmsResponse;
import com.luomor.pcsms.api.utils.SmsRespUtils;
import com.luomor.pcsms.comm.constant.Constant;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.comm.delayedTime.DelayedTime;
import com.luomor.pcsms.comm.exception.SmsBlendException;
import com.luomor.pcsms.huawei.config.HuaweiConfig;
import com.luomor.pcsms.huawei.utils.HuaweiBuilder;
import com.luomor.pcsms.provider.service.AbstractSmsBlend;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import static com.luomor.pcsms.huawei.utils.HuaweiBuilder.listToString;

@Slf4j
public class HuaweiSmsImpl extends AbstractSmsBlend<HuaweiConfig> {

    private volatile int retry = 0;

    public HuaweiSmsImpl(HuaweiConfig config, Executor pool, DelayedTime delayed) {
        super(config, pool, delayed);
    }

    public HuaweiSmsImpl(HuaweiConfig config) {
        super(config);
    }

    @Override
    public String getSupplier() {
        return SupplierConstant.HUAWEI;
    }

    @Override
    public SmsResponse sendMessage(String phone, String message) {
        LinkedHashMap<String, String> mes = new LinkedHashMap<>();
        mes.put(UUID.randomUUID().toString().replaceAll("-", ""), message);
        return sendMessage(phone, getConfig().getTemplateId(), mes);
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
        String url = getConfig().getUrl() + Constant.HUAWEI_REQUEST_URL;
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : messages.entrySet()) {
            list.add(entry.getValue());
        }
        String mess = listToString(list);
        String requestBody = HuaweiBuilder.buildRequestBody(getConfig().getSender(), phone, templateId, mess, getConfig().getStatusCallBack(), getConfig().getSignature());

        Map<String, String> headers = MapUtil.newHashMap(3, true);
        headers.put(Constant.AUTHORIZATION, Constant.HUAWEI_AUTH_HEADER_VALUE);
        headers.put("X-WSSE", HuaweiBuilder.buildWsseHeader(getConfig().getAccessKeyId(), getConfig().getAccessKeySecret()));
        headers.put(Constant.CONTENT_TYPE, Constant.APPLICATION_FROM_URLENCODED);
        SmsResponse smsResponse;
        try {
            smsResponse = getResponse(http.postJson(url, headers, requestBody));
        } catch (SmsBlendException e) {
            smsResponse = errorResp(e.message);
        }
        if (smsResponse.isSuccess() || retry >= getConfig().getMaxRetries()) {
            retry = 0;
            return smsResponse;
        }
        return requestRetry(phone, templateId, messages);
    }

    private SmsResponse requestRetry(String phone, String templateId, LinkedHashMap<String, String> messages) {
        http.safeSleep(getConfig().getRetryInterval());
        retry++;
        log.warn("短信第 {} 次重新发送", retry);
        return sendMessage(phone, templateId, messages);
    }

    @Override
    public SmsResponse massTexting(List<String> phones, String message) {
        return sendMessage(CollUtil.join(phones, ","), message);
    }

    @Override
    public SmsResponse massTexting(List<String> phones, String templateId, LinkedHashMap<String, String> messages) {
        if (Objects.isNull(messages)){
            messages = new LinkedHashMap<>();
        }
        return sendMessage(CollUtil.join(phones, ","), templateId, messages);
    }

    private SmsResponse getResponse(JSONObject resJson) {
        return SmsRespUtils.resp(resJson, "000000".equals(resJson.getStr("code")), getConfigId());
    }

}
