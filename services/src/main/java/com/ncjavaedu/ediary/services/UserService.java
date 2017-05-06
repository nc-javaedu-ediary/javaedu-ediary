package com.ncjavaedu.ediary.services;

import com.ncjavaedu.ediary.model.Role;
import com.ncjavaedu.ediary.model.User;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by abogdanov on 16.03.17.
 */
public interface UserService {
    List<User> getUsers();
    User getUser(String login, String password);
    void saveUser(User user);

    default List<User> getLecturers(){
        return getUsers().stream().filter(u -> u.getRole() == Role.Lecturer).collect(Collectors.toList());
    }
}
