package com.ncjavaedu.ediary.services.stub; /**
 * Created by abogdanov on 16.03.17.
 */

import com.google.gson.reflect.TypeToken;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.UserService;
import com.ncjavaedu.ediary.utils.JSONUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceStub extends RemoteServiceServlet implements UserService{

    private JSONUtils jsonUtils = JSONUtils.getInstance();

    public List<User> getUsers() {
        return jsonUtils.readJsonFromResource("/stub/users.json",
                new TypeToken<List<User>>(){}.getType(), UserServiceStub.class);
    }

    public void saveUser(User user) {
    }
}
