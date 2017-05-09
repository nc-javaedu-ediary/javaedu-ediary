package com.ncjavaedu.ediary.services.impl;

import com.ncjavaedu.ediary.dao.UserDao;
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abogdanov on 24.03.17.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Override
    public List<User> getUsers() {
        return dao.list();
    }

    @Override
    public User getUser(String login, String password) {
        return dao.getUser(login, password);
    }

    @Override
    public void saveUser(User user, List<Course> courses){
        dao.save(user, courses);
    }
}
