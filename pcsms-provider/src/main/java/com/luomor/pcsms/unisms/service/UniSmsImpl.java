package com.luomor.pcsms.unisms.service;

import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import com.luomor.pcsms.api.entity.SmsResponse;
import com.luomor.pcsms.api.utils.SmsRespUtils;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.comm.delayedTime.DelayedTime;
import com.luomor.pcsms.comm.exception.SmsBlendException;
import com.luomor.pcsms.provider.service.AbstractSmsBlend;
import com.luomor.pcsms.unisms.config.UniConfig;
import com.luomor.pcsms.unisms.core.Uni;
import com.luomor.pcsms.unisms.core.UniResponse;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * <p>类名: UniSmsImpl
 * <p>说明：  uniSms短信实现
 *
 * @author Peter
 * 2025/3/26  17:10
 **/
@Slf4j
public class UniSmsImpl extends AbstractSmsBlend<UniConfig> {

    public UniSmsImpl(UniConfig config, Executor pool, DelayedTime delayed) {
        super(config, pool, delayed);
    }

    public UniSmsImpl(UniConfig config) {
        super(config);
    }

    @Override
    public String getSupplier() {
        return SupplierConstant.UNISMS;
    }

    @Override
    public SmsResponse sendMessage(String phone, String message) {
        if ("".equals(getConfig().getTemplateId()) && "".equals(getConfig().getTemplateName())) {
            throw new SmsBlendException("配置文件模板id和模板变量不能为空！");
        }
        LinkedHashMap<String, String> map = new LinkedHashMap<>(1);
        map.put(getConfig().getTemplateName(), message);
        return sendMessage(phone, getConfig().getTemplateId(), map);
    }

    @Override
    public SmsResponse sendMessage(String phone, LinkedHashMap<String, String> messages) {
        return sendMessage(phone, getConfig().getTemplateId(), messages);
    }

    @Override
    public SmsResponse sendMessage(String phone, String templateId, LinkedHashMap<String, String> messages) {
        if (Objects.isNull(messages)){
            messages = new LinkedHashMap<>();
        }
        Map<String, Object> data = MapUtil.newHashMap(4, true);
        data.put("to", Collections.singletonList(phone));
        data.put("signature", getConfig().getSignature());
        data.put("templateId", templateId);
        data.put("templateData", messages);
        return getSmsResponse(data);
    }

    @Override
    public SmsResponse massTexting(List<String> phones, String message) {
        if ("".equals(getConfig().getTemplateId()) && "".equals(getConfig().getTemplateName())) {
            throw new SmsBlendException("配置文件模板id和模板变量不能为空！");
        }
        LinkedHashMap<String, String> map = new LinkedHashMap<>(1);
        map.put(getConfig().getTemplateName(), message);
        return massTexting(phones, getConfig().getTemplateId(), map);
    }

    @Override
    public SmsResponse massTexting(List<String> phones, String templateId, LinkedHashMap<String, String> messages) {
        if (Objects.isNull(messages)){
            messages = new LinkedHashMap<>();
        }
        if (phones.size() > 1000) {
            throw new SmsBlendException("单次发送超过最大发送上限，建议每次群发短信人数低于1000");
        }
        Map<String, Object> data = MapUtil.newHashMap(4, true);
        data.put("to", phones);
        data.put("signature", getConfig().getSignature());
        data.put("templateId", templateId);
        data.put("templateData", messages);
        return getSmsResponse(data);
    }

    private SmsResponse getSmsResponse(Map<String, Object> data) {
        try {
            UniResponse send = Uni.getClient(getConfig().getRetryInterval(), getConfig().getMaxRetries()).request("sms.message.send", data);
            return SmsRespUtils.resp(send, "0".equals(send.code), getConfigId());
        } catch (Exception e) {
            return errorResp(e.getMessage());
        }
    }

}
