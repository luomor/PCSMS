package com.luomor.pcsms.yixintong.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.luomor.pcsms.comm.exception.SmsBlendException;

import java.util.Map;

/**
 * <p>类名: YiXintongUtils
 * <p>说明：联通一信通工具类
 *
 * @author Peter
 * @create 2024-07-31 9:55
 */
public class YiXintongUtils {



    /**
     * 发送post form请求
     *
     * @param url     请求地址
     * @param forms   表单参数
     * @return 返回体
     */
    public static String postForm(String url, Map<String, Object> forms) {
        return postForm(url, null, forms, "gbk");
    }


    /**
     * 发送post form请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param forms   表单参数
     * @param charset 字符集编码
     * @return 返回体
     */
    public static String postForm(String url, Map<String, String> headers, Map<String, Object> forms, String charset) {
        try (HttpResponse response = HttpRequest.post(url)
                .addHeaders(headers)
                .form(forms)
                .charset(charset)
                .execute()) {
            return response.body();
        } catch (Exception e) {
            throw new SmsBlendException(e.getMessage());
        }
    }


}
