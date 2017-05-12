package com.ncjavaedu.ediary.services.stub;

import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.SessionManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * Created by abogdanov on 12.05.17.
 */
@Controller
public class SessionManagementServiceStub implements SessionManagementService {
    private User storedUser = null;

    public User getUser(){
        return storedUser;
    }

    public User setUser(User user){
        this.storedUser = user;
        return storedUser;
    }
}
