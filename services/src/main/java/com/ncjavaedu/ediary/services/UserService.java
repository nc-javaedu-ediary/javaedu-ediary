package com.ncjavaedu.ediary.services;

import com.ncjavaedu.ediary.model.User;
import java.util.List;

/**
 * Created by abogdanov on 16.03.17.
 */
public interface UserService {
    List<User> getUsers();
    User getUser(String login, String password);
    void saveUser(User user);
}
