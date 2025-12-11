package com.luomor.pcsms.solon.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import com.luomor.pcsms.aliyun.config.AlibabaFactory;
import com.luomor.pcsms.api.SmsBlend;
import com.luomor.pcsms.api.universal.SupplierConfig;
import com.luomor.pcsms.api.verify.PhoneVerify;
import com.luomor.pcsms.baidu.config.BaiduFactory;
import com.luomor.pcsms.budingyun.config.BudingV2Factory;
import com.luomor.pcsms.chuanglan.config.ChuangLanFactory;
import com.luomor.pcsms.cloopen.config.CloopenFactory;
import com.luomor.pcsms.comm.constant.Constant;
import com.luomor.pcsms.comm.enums.ConfigType;
import com.luomor.pcsms.comm.utils.SmsUtils;
import com.luomor.pcsms.core.datainterface.SmsReadConfig;
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
import com.luomor.pcsms.jdcloud.config.JdCloudFactory;
import com.luomor.pcsms.jg.config.JgFactory;
import com.luomor.pcsms.lianlu.config.LianLuFactory;
import com.luomor.pcsms.luosimao.config.LuoSiMaoFactory;
import com.luomor.pcsms.mas.config.MasFactory;
import com.luomor.pcsms.netease.config.NeteaseFactory;
import com.luomor.pcsms.provider.config.SmsConfig;
import com.luomor.pcsms.provider.factory.BaseProviderFactory;
import com.luomor.pcsms.provider.factory.ProviderFactoryHolder;
import com.luomor.pcsms.qiniu.config.QiNiuFactory;
import com.luomor.pcsms.solon.adaptor.ConfigCombineMapAdaptor;
import com.luomor.pcsms.submail.config.SubMailFactory;
import com.luomor.pcsms.tencent.config.TencentFactory;
import com.luomor.pcsms.unisms.config.UniFactory;
import com.luomor.pcsms.yixintong.config.YiXintongFactory;
import com.luomor.pcsms.yunpian.config.YunPianFactory;
import com.luomor.pcsms.zhutong.config.ZhutongFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;


@Slf4j
public class SmsBlendsInitializer {
    private final List<BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig>> factoryList;

    private final SmsConfig smsConfig;
    private final Map<String, Map<String, Object>> blends;
    private final List<SmsReadConfig> extendsSmsConfigs;

    public SmsBlendsInitializer(List<BaseProviderFactory<? extends SmsBlend, ? extends SupplierConfig>> factoryList,
                                SmsConfig smsConfig,
                                Map<String, Map<String, Object>> blends,
                                List<SmsReadConfig> extendsSmsConfigs){
        this.factoryList = factoryList;
        this.smsConfig = smsConfig;
        this.blends = blends;
        this.extendsSmsConfigs = extendsSmsConfigs;

        this.initDo();
    }

    private void initDo() {
        this.registerDefaultFactory();
        // 注册短信对象工厂
        ProviderFactoryHolder.registerFactory(factoryList);
        //如果手机号校验器存在实现，则注册手机号校验器（暂不可用）
        ServiceLoader<PhoneVerify> loader = ServiceLoader.load(PhoneVerify.class);
        if (loader.iterator().hasNext()) {
            loader.forEach(f -> SmsProxyFactory.addPreProcessor(new CoreMethodParamValidateProcessor(f)));
        } else {
            SmsProxyFactory.addPreProcessor(new CoreMethodParamValidateProcessor(null));
        }
        //注册执行器实现
        if(this.smsConfig.getRestricted()){
            SmsProxyFactory.addPreProcessor(new RestrictedProcessor());
            SmsProxyFactory.addPreProcessor(new BlackListProcessor());
            SmsProxyFactory.addPreProcessor(new BlackListRecordingProcessor());
        }
        if (ConfigType.YAML.equals(this.smsConfig.getConfigType())) {
            //持有初始化配置信息
            Map<String, Map<String, Object>> blendsInclude = new ConfigCombineMapAdaptor<String, Map<String, Object>>();
            blendsInclude.putAll(this.blends);
            int num = 0;
            for (SmsReadConfig smsReadConfig : extendsSmsConfigs) {
                String key = SmsReadConfig.class.getSimpleName() + num;
                Map<String, Object> insideMap = new HashMap<>();
                insideMap.put(key, smsReadConfig);
                blendsInclude.put(key, insideMap);
                num++;
            }
            EnvirmentHolder.frozenEnvirmet(smsConfig, blendsInclude);
            // 解析供应商配置
            for (String configId : blends.keySet()) {
                Map<String, Object> configMap = blends.get(configId);
                Object supplierObj = configMap.get(Constant.SUPPLIER_KEY);
                String supplier = supplierObj == null ? "" : String.valueOf(supplierObj);
                supplier = StrUtil.isEmpty(supplier) ? configId : supplier;
                BaseProviderFactory<SmsBlend, SupplierConfig> providerFactory = (BaseProviderFactory<SmsBlend, com.luomor.pcsms.api.universal.SupplierConfig>) ProviderFactoryHolder.requireForSupplier(supplier);
                if (providerFactory == null) {
                    log.warn("创建\"{}\"的短信服务失败，未找到供应商为\"{}\"的服务", configId, supplier);
                    continue;
                }
                configMap.put("config-id", configId);
                SmsUtils.replaceKeysSeparator(configMap, "-", "_");
                JSONObject configJson = new JSONObject(configMap);
                com.luomor.pcsms.api.universal.SupplierConfig supplierConfig = JSONUtil.toBean(configJson, providerFactory.getConfigClass());
                SmsFactory.createSmsBlend(supplierConfig);
            }
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
            if (SmsUtils.isClassExists("com.jdcloud.sdk.auth.CredentialsProvider")) {
                ProviderFactoryHolder.registerFactory(JdCloudFactory.instance());
            }
            log.debug("加载内置运营商完成！");
        }
    }

}
