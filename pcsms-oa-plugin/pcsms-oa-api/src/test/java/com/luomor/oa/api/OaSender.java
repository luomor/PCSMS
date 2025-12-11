package com.luomor.oa.api;

import com.luomor.oa.comm.entity.Request;
import com.luomor.oa.comm.entity.Response;
import com.luomor.oa.comm.enums.MessageType;

public interface OaSender {

    Response Sender(Request request, MessageType messageType);
}
