package com.ncjavaedu.ediary.frontend.server;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface frontendServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}