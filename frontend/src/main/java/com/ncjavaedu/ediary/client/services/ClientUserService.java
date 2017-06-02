package com.ncjavaedu.ediary.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;

import java.util.List;

@RemoteServiceRelativePath("userService")
public interface ClientUserService extends RemoteService {
    List<UserDTO> getUsers();
    UserDTO getUser(String login, String password);
    List<UserDTO> getLecturers();
    UserDTO saveUser(UserDTO dto, List<CourseDTO> courseDTOs);
    UserDTO deleteUser(UserDTO dto);

    public static class App {
        private static ClientUserServiceAsync instance = GWT.create(ClientUserService.class);

        public static synchronized ClientUserServiceAsync getInstance() {
            return instance;
        }
    }
}