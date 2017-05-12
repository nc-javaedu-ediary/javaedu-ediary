package com.ncjavaedu.ediary.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ncjavaedu.ediary.client.model.UserDTO;

/**
 * Created by abogdanov on 11.05.17.
 */
@RemoteServiceRelativePath("sessionManagementService")
public interface ClientSessionManagementService extends RemoteService {
    UserDTO saveUser(UserDTO dto);
    UserDTO getUser();

    public static class App {
        private static ClientSessionManagementServiceAsync instance = GWT.create(ClientSessionManagementService.class);

        public static synchronized ClientSessionManagementServiceAsync getInstance() {
            return instance;
        }
    }
}
