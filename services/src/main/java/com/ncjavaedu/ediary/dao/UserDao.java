package com.ncjavaedu.ediary.dao;

import com.ncjavaedu.ediary.model.User;
import java.util.List;

public interface UserDao {
    List<User> list();
    void save(User user);
}
