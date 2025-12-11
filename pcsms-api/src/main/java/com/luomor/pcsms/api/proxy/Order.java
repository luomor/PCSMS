package com.luomor.pcsms.api.proxy;
/**
 * 排序接口 用户拦截器排序并进行有序执行，请注意，排序值应大于等于0。
 * 拦截器的排序依照从小到大的规则进行排序
 * @author Peter
 * @since 2025/10/27 13:03
 */
public interface Order {

    default int getOrder(){
        return 999;
    }
}
