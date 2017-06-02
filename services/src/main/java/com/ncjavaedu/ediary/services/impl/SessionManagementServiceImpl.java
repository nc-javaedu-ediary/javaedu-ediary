package com.ncjavaedu.ediary.services.impl;

import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.SessionManagementService;
import org.springframework.stereotype.Service;

/**
 * Created by abogdanov on 12.05.17.
 */
@Service
public class SessionManagementServiceImpl implements SessionManagementService {
    private User storedUser = null;

    public User getUser(){
        return storedUser;
    }

    public User setUser(User user){
        storedUser = user;
        return storedUser;
    }
}
