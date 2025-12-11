package com.luomor.email.core.factory;

import com.luomor.email.api.Blacklist;
import com.luomor.email.api.MailClient;
import com.luomor.email.comm.config.MailSmtpConfig;
import com.luomor.email.comm.errors.MailException;
import com.luomor.email.core.service.MailBuild;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

/**
 * MailFactory
 * <p> 配置工厂
 * @author Peter
 * 2025/6/8  22:35
 **/
public class MailFactory{
    private static final Map<Object,MailSmtpConfig> CONFIGS = new HashMap<>();

    /**
     *  createMailClient
     * <p>从工厂获取一个邮件发送实例
     * @param key 配置的标识key
     * @author Peter
    */
    public static MailClient createMailClient(Object key){
        try {
            return MailBuild.build(CONFIGS.get(key));
        } catch (MessagingException e) {
            throw new MailException(e);
        }
    }


    /**
     *  createMailClient
     * <p>从工厂获取一个邮件发送实例,该实例发送短信将依照黑名单中的数据进行过滤
     * @param key 配置的标识key
     * @param blacklist 黑名单接口，实例将从这里获取黑名单数据
     * @author Peter
    */
    public static MailClient createMailClient(Object key, Blacklist blacklist){
        try {
            return MailBuild.build(CONFIGS.get(key),blacklist);
        } catch (MessagingException e) {
            throw new MailException(e);
        }
    }

    /**
     *  set
     * <p>将一个配置对象交给工厂
     * @param key 标识
     * @param config 配置对象
     * @author Peter
    */
    public static void put(Object key, MailSmtpConfig config){
        CONFIGS.put(key,config);
    }

}
