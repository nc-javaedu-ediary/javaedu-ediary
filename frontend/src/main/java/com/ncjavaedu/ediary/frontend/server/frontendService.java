package com.ncjavaedu.ediary.frontend.server;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("MySampleGWTApplicationService")
public interface frontendService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use MySampleGWTApplicationService.App.getInstance() to access static instance of MySampleGWTApplicationServiceAsync
     */
    public static class App {
        private static frontendServiceAsync ourInstance = GWT.create(frontendService.class);

        public static synchronized frontendServiceAsync getInstance() {
            return ourInstance;
        }
    }
}