package com.ncjavaedu.ediary.frontend.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class frontendServiceImpl extends RemoteServiceServlet implements frontendService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }

    public String getUser(){
        return "";
    }
}