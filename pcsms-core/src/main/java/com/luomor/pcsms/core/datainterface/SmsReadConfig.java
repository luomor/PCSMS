package com.luomor.pcsms.core.datainterface;

import com.luomor.pcsms.provider.config.BaseConfig;

import java.util.List;

/**
 * SmsReadConfig
 * <p> 读取配置接口，实现该接口中的方法则可以按照自己的形式进行配置的读取
 * <p>这样只关注最终的配置数据而不关注配置的来源，用户可以自由的选择数据来源的方式</p>
 * <p>该种方式读取配置并非在启动阶段完成，而是在方法第一次调用期间完成</p>
 * @author Peter
 * 2025/8/1  12:06
 **/
public interface SmsReadConfig {

    /**
     *  getSupplierConfig
     * <p> 通过配置ID获取一个厂商的配置
     * @param configId 配置id
     * @author Peter
    */
    BaseConfig getSupplierConfig(String configId);

    /**
     *  getSupplierConfigList
     * <p> 获取多个厂商的配置，会同时加载进框架中
     * @author Peter
    */
    List<BaseConfig> getSupplierConfigList();

}
