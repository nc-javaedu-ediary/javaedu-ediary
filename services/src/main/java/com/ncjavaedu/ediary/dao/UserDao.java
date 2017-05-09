package com.ncjavaedu.ediary.dao;

import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.model.User;
import java.util.List;

public interface UserDao {
    List<User> list();
    User getUser(String login, String password);
    void save(User user, List<Course> courses);
}
