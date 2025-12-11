package com.luomor.pcsms.starter.holder;

import com.luomor.pcsms.api.dao.SmsDao;
import com.luomor.pcsms.starter.utils.SmsSpringUtils;

public class SpringSmsDaoHolder {
    public static SmsDao getSmsDao() {
        return SmsSpringUtils.getApplicationContext().getBean(SmsDao.class);
    }
}
