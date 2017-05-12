package com.ncjavaedu.ediary.server.services;

import com.ncjavaedu.ediary.client.model.UserDTO;

import com.ncjavaedu.ediary.client.services.ClientSessionManagementService;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.SessionManagementService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by abogdanov on 11.05.17.
 */
public class ClientSessionManagementServiceImpl extends BaseServiceImpl implements ClientSessionManagementService {

    @Autowired
    private SessionManagementService sessionManagementService;

    @Override
    public UserDTO saveUser(UserDTO dto){
        if(dto !=  null) {
            User u = ServiceUtils.userDtoToUser(dto);
            User rcvUser = sessionManagementService.setUser(u);
            UserDTO retUser;
            if (rcvUser != null) {
                retUser = ServiceUtils.userToDto(rcvUser);
            } else
                retUser = null;
            return retUser;
        }
        sessionManagementService.setUser(null);
        return null;
    }

    @Override
    public UserDTO getUser(){
        User rcvUser = sessionManagementService.getUser();
        UserDTO retUser;
        if(rcvUser != null)
            retUser = ServiceUtils.userToDto(rcvUser);
        else
            retUser = null;
        return retUser;
    }
}
