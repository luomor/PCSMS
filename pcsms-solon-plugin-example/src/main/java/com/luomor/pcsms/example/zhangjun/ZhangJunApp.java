package com.luomor.pcsms.example.zhangjun;

import com.luomor.pcsms.core.factory.SmsFactory;
import org.noear.solon.Solon;

/**
 * 自定义广州掌骏短信实现
 *
 * @author Peter
 */
public class ZhangJunApp {

    public static void main(String[] args) {
        Solon.start(ZhangJunApp.class, args);
        SmsFactory.getBySupplier("zhangjun").sendMessage("17*****598", "154468");
    }

}
