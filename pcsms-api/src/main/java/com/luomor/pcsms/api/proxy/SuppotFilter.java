package com.luomor.pcsms.api.proxy;

import java.util.List;
/**
 * 支持接口，如果执行器需要根据支持厂商加载，那可以实现此接口
 *
 * @author Peter
 * @since 2025/10/27 13:03
 */
public interface SuppotFilter{
    List<String> getSupports();
}
