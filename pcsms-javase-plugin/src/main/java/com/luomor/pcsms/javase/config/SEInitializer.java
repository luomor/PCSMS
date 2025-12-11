package com.luomor.pcsms.javase.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import com.luomor.pcsms.aliyun.config.AlibabaFactory;
import com.luomor.pcsms.api.SmsBlend;
import com.luomor.pcsms.api.dao.SmsDao;
import com.luomor.pcsms.api.dao.SmsDaoDefaultImpl;
import com.luomor.pcsms.api.universal.SupplierConfig;
import com.luomor.pcsms.api.verify.PhoneVerify;
import com.luomor.pcsms.baidu.config.BaiduFactory;
import com.luomor.pcsms.budingyun.config.BudingV2Factory;
import com.luomor.pcsms.chuanglan.config.ChuangLanFactory;
import com.luomor.pcsms.cloopen.config.CloopenFactory;
import com.luomor.pcsms.comm.constant.Constant;
import com.luomor.pcsms.comm.exception.SmsBlendException;
import com.luomor.pcsms.comm.utils.SmsUtils;
import com.luomor.pcsms.core.factory.SmsFactory;
import com.luomor.pcsms.core.proxy.EnvirmentHolder;
import com.luomor.pcsms.core.proxy.SmsProxyFactory;
import com.luomor.pcsms.core.proxy.processor.BlackListProcessor;
import com.luomor.pcsms.core.proxy.processor.BlackListRecordingProcessor;
import com.luomor.pcsms.core.proxy.processor.CoreMethodParamValidateProcessor;
import com.luomor.pcsms.core.proxy.processor.RestrictedProcessor;
import com.luomor.pcsms.ctyun.config.CtyunFactory;
import com.luomor.pcsms.danmi.config.DanMiFactory;
import com.luomor.pcsms.dingzhong.config.DingZhongFactory;
import com.luomor.pcsms.emay.config.EmayFactory;
import com.luomor.pcsms.huawei.config.HuaweiFactory;
import com.luomor.pcsms.javase.util.YamlUtils;
import com.luomor.pcsms.jdcloud.config.JdCloudFactory;
import com.luomor.pcsms.jg.config.JgFactory;
import com.luomor.pcsms.lianlu.config.LianLuFactory;
import com.luomor.pcsms.luosimao.config.LuoSiMaoFactory;
import com.luomor.pcsms.mas.config.MasFactory;
import com.luomor.pcsms.netease.config.NeteaseFactory;
import com.luomor.pcsms.provider.config.SmsConfig;
import com.luomor.pcsms.provider.factory.BaseProviderFactory;
import com.luomor.pcsms.provider.factory.BeanFactory;
import com.luomor.pcsms.provider.factory.ProviderFactoryHolder;
import com.luomor.pcsms.qiniu.config.QiNiuFactory;
import com.luomor.pcsms.submail.config.SubMailFactory;
import com.luomor.pcsms.tencent.config.TencentFactory;
import com.luomor.pcsms.unisms.config.UniFactory;
import com.luomor.pcsms.yixintong.config.YiXintongFactory;
import com.luomor.pcsms.yunpian.config.YunPianFactory;
import com.luomor.pcsms.zhutong.config.ZhutongFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 初始化类
 */
@Slf4j
public class SEInitializer {

    private static final SEInitializer INSTANCE = new SEInitializer();

    public static SEInitializer initializer() {
        return INSTANCE;
    }

    private SmsDao smsDao;

    /**
     * 默认从pcsms.yml文件中读取配置
     */
    public void fromYaml() {
        ClassPathResource yamlResouce = new ClassPathResource("pcsms.yml");
        this.fromYaml(yamlResouce.readUtf8Str());
    }

    /**
     * 从yaml中读取配置
     *
     * @param yaml yaml配置字符串
     */
    public void fromYaml(String yaml) {
        InitConfig config = YamlUtils.toBean(yaml, InitConfig.class);
        this.initConfig(config);
    }

    /**
     * 从json中读取配置
     *
     * @param json json配置字符串
     */
    public void fromJson(String json) {
        InitConfig config = JSONUtil.toBean(json, InitConfig.class);
        this.initConfig(config);
    }

    /**
     * 从配置bean对象中加载配置
     *
     * @param smsConfig  短信公共配置
     * @param configList 各短信服务商配置列表
     */
    public void fromConfig(SmsConfig smsConfig, List<SupplierConfig> configList) {
        // 注册默认工厂
        registerDefaultFactory();
        // 初始化SmsConfig整体配置文件
        BeanUtil.copyProperties(smsConfig, BeanFactory.getSmsConfig());
        // 创建短信服务对象
        if (CollUtil.isEmpty(configList)) {
            return;
        }
        try{
            Map<String, Map<String, Object>> blends = new HashMap<>();
            for (SupplierConfig supplierConfig : configList) {
                Map<String, Object> param = new HashMap<>();
                String channel = supplierConfig.getSupplier();
                Class<? extends SupplierConfig> clazz = supplierConfig.getClass();
                BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    Method readMethod = propertyDescriptor.getReadMethod();
                    Object item = readMethod.invoke(supplierConfig);
                    param.put(propertyDescriptor.getName(),item);
                }
                blends.put(channel,param);
            }
            //持有初始化配置信息
            EnvirmentHolder.frozenEnvirmet(smsConfig, blends);

            //注册执行器实现
            SmsProxyFactory.addPreProcessor(new RestrictedProcessor());
            SmsProxyFactory.addPreProcessor(new BlackListProcessor());
            SmsProxyFactory.addPreProcessor(new BlackListRecordingProcessor());
            //如果手机号校验器存在实现，则注册手机号校验器
            ServiceLoader<PhoneVerify> loader = ServiceLoader.load(PhoneVerify.class);
            if (loader.iterator().hasNext()) {
                loader.forEach(f -> SmsProxyFactory.addPreProcessor(new CoreMethodParamValidateProcessor(f)));
            } else {
                SmsProxyFactory.addPreProcessor(new CoreMethodParamValidateProcessor(null));
            }
        }catch (Exception e){
            log.error("配置对象转换配置信息失败，但不影响基础功能的使用。【注意】：未加载PCSMS扩展功能模块，拦截器，参数校验可能失效！");
        }
        for (SupplierConfig supplierConfig : configList) {
            SmsFactory.createSmsBlend(supplierConfig);
        }
    }

    /**
     * 注册服务商工厂
     *
     * @param factory 服务商工厂
     */
    public SEInitializer registerFactory(BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig> factory) {
        ProviderFactoryHolder.registerFactory(factory);
        return this;
    }

    /**
     * 注册DAO实例
     *
     * @param smsDao DAO实例
     */
    public SEInitializer registerSmsDao(SmsDao smsDao) {
        if (smsDao == null) {
            throw new SmsBlendException("注册DAO实例失败，实例不能为空");
        }
        this.smsDao = smsDao;
        SESmsDaoHolder.setSmsDao(smsDao);
        return this;
    }

    private void initConfig(InitConfig config) {
        if (config == null) {
            log.error("初始化配置失败");
            throw new SmsBlendException("初始化配置失败");
        }
        InitSmsConfig smsConfig = config.getSms();
        if (smsConfig == null) {
            log.error("初始化配置失败");
            throw new SmsBlendException("初始化配置失败");
        }

        //注册默认DAO实例
        if (this.smsDao == null) {
            this.registerSmsDao(SmsDaoDefaultImpl.getInstance());
        }

        //注册默认工厂
        registerDefaultFactory();

        //初始化SmsConfig整体配置文件
        BeanUtil.copyProperties(smsConfig, BeanFactory.getSmsConfig());

        // 解析服务商配置
        Map<String, Map<String, Object>> blends = smsConfig.getBlends();

        //持有初始化配置信息
        EnvirmentHolder.frozenEnvirmet(smsConfig, blends);

        //注册执行器实现
        SmsProxyFactory.addPreProcessor(new RestrictedProcessor());
        SmsProxyFactory.addPreProcessor(new BlackListProcessor());
        SmsProxyFactory.addPreProcessor(new BlackListRecordingProcessor());
        //如果手机号校验器存在实现，则注册手机号校验器
        ServiceLoader<PhoneVerify> loader = ServiceLoader.load(PhoneVerify.class);
        if (loader.iterator().hasNext()) {
            loader.forEach(f -> SmsProxyFactory.addPreProcessor(new CoreMethodParamValidateProcessor(f)));
        } else {
            SmsProxyFactory.addPreProcessor(new CoreMethodParamValidateProcessor(null));
        }
        for (String configId : blends.keySet()) {
            Map<String, Object> configMap = blends.get(configId);
            Object supplierObj = configMap.get(Constant.SUPPLIER_KEY);
            String supplier = supplierObj == null ? "" : String.valueOf(supplierObj);
            supplier = StrUtil.isEmpty(supplier) ? configId : supplier;
            BaseProviderFactory<SmsBlend, SupplierConfig> providerFactory = (BaseProviderFactory<SmsBlend, SupplierConfig>) ProviderFactoryHolder.requireForSupplier(supplier);
            if (providerFactory == null) {
                log.warn("创建\"{}\"的短信服务失败，未找到服务商为\"{}\"的服务", configId, supplier);
                continue;
            }
            configMap.put("config-id", configId);
            SmsUtils.replaceKeysSeparator(configMap, "-", "_");
            JSONObject configJson = new JSONObject(configMap);
            SupplierConfig supplierConfig = JSONUtil.toBean(configJson, providerFactory.getConfigClass());
            SmsFactory.createSmsBlend(supplierConfig);
        }
    }

    /**
     * 注册默认工厂实例
     */
    private void registerDefaultFactory() {
        ProviderFactoryHolder.registerFactory(AlibabaFactory.instance());
        ProviderFactoryHolder.registerFactory(CloopenFactory.instance());
        ProviderFactoryHolder.registerFactory(CtyunFactory.instance());
        ProviderFactoryHolder.registerFactory(EmayFactory.instance());
        ProviderFactoryHolder.registerFactory(HuaweiFactory.instance());
        ProviderFactoryHolder.registerFactory(NeteaseFactory.instance());
        ProviderFactoryHolder.registerFactory(TencentFactory.instance());
        ProviderFactoryHolder.registerFactory(UniFactory.instance());
        ProviderFactoryHolder.registerFactory(YunPianFactory.instance());
        ProviderFactoryHolder.registerFactory(ZhutongFactory.instance());
        ProviderFactoryHolder.registerFactory(LianLuFactory.instance());
        ProviderFactoryHolder.registerFactory(DingZhongFactory.instance());
        ProviderFactoryHolder.registerFactory(QiNiuFactory.instance());
        ProviderFactoryHolder.registerFactory(ChuangLanFactory.instance());
        ProviderFactoryHolder.registerFactory(JgFactory.instance());
        ProviderFactoryHolder.registerFactory(BudingV2Factory.instance());
        ProviderFactoryHolder.registerFactory(MasFactory.instance());
        ProviderFactoryHolder.registerFactory(BaiduFactory.instance());
        ProviderFactoryHolder.registerFactory(LuoSiMaoFactory.instance());
        ProviderFactoryHolder.registerFactory(SubMailFactory.instance());
        ProviderFactoryHolder.registerFactory(DanMiFactory.instance());
        ProviderFactoryHolder.registerFactory(YiXintongFactory.instance());
        if (SmsUtils.isClassExists("com.jdcloud.sdk.auth.CredentialsProvider")) {
            ProviderFactoryHolder.registerFactory(JdCloudFactory.instance());
        }
    }

    /**
     * 初始化配置bean
     */
    @Data
    @EqualsAndHashCode
    @ToString
    public static class InitConfig {
        private InitSmsConfig sms;
    }

    /**
     * 初始化短信配置bean
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class InitSmsConfig extends SmsConfig {
        private Map<String, Map<String, Object>> blends;
    }

}
