package com.luomor.pcsms.solon.adaptor;

import cn.hutool.core.bean.BeanUtil;
import com.luomor.pcsms.core.datainterface.SmsReadConfig;
import com.luomor.pcsms.provider.config.BaseConfig;

import java.util.*;

public class ConfigCombineMapAdaptor<S, M> extends HashMap {
    @Override
    public M get(Object key) {
        Object o = super.get(key);
        if (null == o){
            Set configKeySet = this.keySet();
            for (Object insideMapKey : configKeySet) {
                if (((String)insideMapKey).startsWith(SmsReadConfig.class.getSimpleName())){
                    Map smsBlendsConfigInsideMap  = (Map) this.get(insideMapKey);
                    SmsReadConfig config = (SmsReadConfig) smsBlendsConfigInsideMap.get(insideMapKey);
                    BaseConfig supplierConfig = config.getSupplierConfig((String)key);
                    List<BaseConfig> supplierConfigList = config.getSupplierConfigList();
                    if (null == supplierConfigList){
                        supplierConfigList = new ArrayList<>();
                    }
                    if (null != supplierConfig){
                        supplierConfigList.add(supplierConfig);
                    }
                    for (BaseConfig baseConfig : supplierConfigList) {
                        if (key.equals(baseConfig.getConfigId())){
                            Map<String, Object> configMap = BeanUtil.beanToMap(baseConfig);
                            this.put(baseConfig.getConfigId(),configMap);
                            return (M)configMap;
                        }
                    }
                }
            }
            return null;
        }
        return (M)o;
    }
}
