package com.luomor.pcsms.api.callback;

import com.luomor.pcsms.api.entity.SmsResponse;

@FunctionalInterface
public interface CallBack {
    void callBack(SmsResponse smsResponse);
}
