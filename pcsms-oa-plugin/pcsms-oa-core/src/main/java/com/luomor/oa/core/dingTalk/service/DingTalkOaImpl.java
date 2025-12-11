package com.luomor.oa.core.dingTalk.service;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.luomor.oa.comm.entity.Request;
import com.luomor.oa.comm.entity.Response;
import com.luomor.oa.comm.enums.MessageType;
import com.luomor.oa.comm.errors.OaException;
import com.luomor.oa.core.dingTalk.config.DingTalkConfig;
import com.luomor.oa.core.dingTalk.utils.DingTalkBuilder;
import com.luomor.oa.core.provider.service.AbstractOaBlend;
import com.luomor.oa.core.support.HttpClientImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

import static com.luomor.oa.comm.enums.OaType.DING_TALK;

/**
 * @author Peter
 * 2023-10-22 21:01
 */
@Slf4j
public class DingTalkOaImpl extends AbstractOaBlend<DingTalkConfig> {

    private final HttpClientImpl httpClient = new HttpClientImpl();

    /**
     * 建造一个微信通知对象服务
     */
    public DingTalkOaImpl(DingTalkConfig config) {
        super(config);
    }

    /**
     * 建造一个微信通知对象服务
     */
    public DingTalkOaImpl(DingTalkConfig config, Executor pool) {
        super(config,pool);
    }

    @Override
    public String getSupplier() {
        return getConfig().getSupplier();
    }

    @Override
    public Response sender(Request request, MessageType messageType) {
        if (Objects.isNull(request.getContent())) {
            throw new OaException("消息体content不能为空",getConfig().getConfigId());
        }
        StringBuilder webhook = new StringBuilder();
        JSONObject message = null;
        DingTalkConfig config = getConfig();
        webhook.append(DING_TALK.getUrl());
        webhook.append(config.getTokenId());
        String sign = config.getSign();
        if (!Objects.isNull(sign)) {
            sign = DingTalkBuilder.sign(sign);
            webhook.append(sign);
        }
        message = DingTalkBuilder.createMessage(request, messageType);
        String post;
        try {
            post = httpClient.post(webhook, getHeaders(), message);
            log.info("请求返回结果：" + post);
        } catch (Exception e) {
            log.warn("请求失败问题：" + e.getMessage());
            throw new OaException(e.getMessage(),getConfig().getConfigId());
        }
        // 后续解析响应体提取errorCode判断是否成功
        return new Response(true, post, config.getConfigId());
    }

    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
