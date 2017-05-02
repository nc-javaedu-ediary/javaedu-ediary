package com.ncjavaedu.ediary.server.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.RoleDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.client.services.ClientCourseService;
import com.ncjavaedu.ediary.client.services.ClientUserService;
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.model.Role;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.UserService;
import com.sencha.gxt.widget.core.client.info.Info;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//import com.ncjavaedu.ediary.client.model.UserDTO;

public class ClientUserServiceImpl extends BaseServiceImpl implements ClientUserService {

    @Autowired
    private UserService userService;

    @Override
    public List<UserDTO> getUsers() {
        List<User> remoteUsers = userService.getUsers();
        List<UserDTO> users = new ArrayList<>();
        for (User user : remoteUsers){
            UserDTO dto = ServiceUtils.userToDto(user);
            users.add(dto);
        }
        return users;
    }

    @Override
    public UserDTO saveUser(UserDTO dto) {
        User user = ServiceUtils.userDtoToUser(dto);
        userService.saveUser(user);

        //Update userId after save
        dto.setUserId(user.getUserId());
        return dto;
    }

    @Override
    public UserDTO getUser(String login, String password) {
        User user = userService.getUser(login, password);
        return (user == null) ? null : ServiceUtils.userToDto(user);
    }
}
