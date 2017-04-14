package com.ncjavaedu.ediary.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ncjavaedu.ediary.model.User;
import java.util.List;

/**
 * Created by abogdanov on 16.03.17.
 */
@RemoteServiceRelativePath("/")
public interface UserService extends RemoteService {
    List<User> getUsers();

    void saveUser(User user);
}
