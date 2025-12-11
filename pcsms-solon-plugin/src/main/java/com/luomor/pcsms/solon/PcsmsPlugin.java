package com.luomor.pcsms.solon;

import com.luomor.pcsms.solon.config.SmsMainConfigure;
import com.luomor.pcsms.solon.config.SupplierConfigure;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.Plugin;

/**
 * @author Peter
 */
public class PcsmsPlugin implements Plugin {
    @Override
    public void start(AppContext context) {
        context.beanMake(SmsMainConfigure.class);
        context.beanMake(SupplierConfigure.class);
    }
}
