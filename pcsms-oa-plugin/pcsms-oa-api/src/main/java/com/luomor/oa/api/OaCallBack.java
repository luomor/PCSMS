package com.luomor.oa.api;

import com.luomor.oa.comm.entity.Response;

/**
 * @author Peter
 * 2023-10-28 14:26
 */
@FunctionalInterface
public interface OaCallBack {
    void callBack(Response smsResponse);
}
