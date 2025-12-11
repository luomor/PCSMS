package com.luomor.pcsms.javase.config;

import lombok.Getter;
import com.luomor.pcsms.api.dao.SmsDao;

public class SESmsDaoHolder {

    @Getter
    private static SmsDao smsDao = null;

    public static void setSmsDao(SmsDao smsDao) {
        SESmsDaoHolder.smsDao = smsDao;
    }
}
