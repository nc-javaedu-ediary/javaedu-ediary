package com.ncjavaedu.ediary.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ncjavaedu.ediary.client.model.RoleDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.client.services.ClientUserService;
import com.ncjavaedu.ediary.model.Role;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.UserService;
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
            UserDTO dto = userToDto(user);
            users.add(dto);
        }
        return users;
    }

    @Override
    public UserDTO saveUser(UserDTO dto) {
        User user = userDtoToUser(dto);
        userService.saveUser(user);

        //Update userId after save
        dto.setUserId(user.getUserId());
        return dto;
    }

    @Override
    public UserDTO getUser(String login, String password) {
        User user = userService.getUser(login, password);
        return (user == null) ? null : userToDto(user);
    }

    private UserDTO userToDto(User user){
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUniversity(user.getUniversity());
        if(user.getRole() != null) {
            dto.setRole(RoleDTO.values()[user.getRole().ordinal()]);
        }
        dto.setLogin(user.getLogin());
        dto.setPassword(user.getPassword());
        return dto;
    }

    private User userDtoToUser(UserDTO dto){
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUniversity(dto.getUniversity());
        if(dto.getRole() != null){
            user.setRole(Role.values()[dto.getRole().ordinal()]);
        }
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        return user;
    }
}
