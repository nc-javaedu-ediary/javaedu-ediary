package com.ncjavaedu.ediary.services.impl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ncjavaedu.ediary.dao.UserDao;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abogdanov on 24.03.17.
 */
@Service
public class UserServiceImpl extends RemoteServiceServlet implements UserService {

    @Autowired
    private UserDao dao;

    public List<User> getUsers() {
        return dao.list();
    }

    public void saveUser(User user) {
       dao.save(user);
    }
}
