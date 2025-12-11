package com.luomor.pcsms.solon.holder;

import com.luomor.pcsms.api.dao.SmsDao;
import org.noear.solon.Solon;

public class SolonSmsDaoHolder {
    public static SmsDao getSmsDao() {
        return Solon.context().getBean(SmsDao.class);
    }
}
