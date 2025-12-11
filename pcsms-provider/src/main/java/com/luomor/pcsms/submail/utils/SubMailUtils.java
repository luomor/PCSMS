package com.luomor.pcsms.submail.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import com.luomor.pcsms.comm.constant.Constant;
import com.luomor.pcsms.comm.utils.SmsUtils;

import java.util.LinkedHashMap;

/**
 * <p>类名: SubMailUtils
 *
 * @author Peter
 * 2025/6/22  13:59
 **/
@Slf4j
public class SubMailUtils {

    public static LinkedHashMap<String, String> buildHeaders(){
        LinkedHashMap<String, String> headers = new LinkedHashMap<>(1);
        headers.put(Constant.ACCEPT, Constant.APPLICATION_JSON);
        headers.put(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);
        return headers;
    }


    public static String signature(LinkedHashMap<String, Object> body, String signType, String accessKeyId, String accessKeySecret, String... excludes){
        if (StrUtil.containsAnyIgnoreCase(signType, DigestAlgorithm.MD5.getValue(), DigestAlgorithm.SHA1.getValue())){
            StringBuilder sb = new StringBuilder();
            sb.append(accessKeyId).append(accessKeySecret);
            if ("2".equals(Convert.toStr(body.get("sign_version")))){
                sb.append(SmsUtils.sortedParamsAsc(body, excludes));
            }else {
                sb.append(SmsUtils.sortedParamsAsc(body));
            }
            sb.append(accessKeyId).append(accessKeySecret);
            if (signType.equalsIgnoreCase(DigestAlgorithm.MD5.getValue())){
                return DigestUtil.md5Hex(sb.toString());
            }else {
                return DigestUtil.sha1Hex(sb.toString());
            }
        }else {
            return accessKeySecret;
        }
    }
}
