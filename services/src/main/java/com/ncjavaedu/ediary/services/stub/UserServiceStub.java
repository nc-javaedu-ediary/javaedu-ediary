package com.ncjavaedu.ediary.services.stub; /**
 * Created by abogdanov on 16.03.17.
 */

import com.google.gson.reflect.TypeToken;
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.UserService;
import com.ncjavaedu.ediary.utils.JSONUtils;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceStub implements UserService {

    private JSONUtils jsonUtils = JSONUtils.getInstance();

    public List<User> getUsers() {
        return jsonUtils.readJsonFromResource("/stub/users.json",
                new TypeToken<List<User>>(){}.getType(), UserServiceStub.class);
    }

    @Override
    public User getUser(String login, String password) {
        if (login == null || password == null)
            return null;

        List<User> users = getUsers();
        for (User user : users){
            if (login.equals(user.getLogin()) && password.equals(user.getPassword())){
                return user;
            }
        }
        return null;
    }

    @Override
    public void saveUser(User user, List<Course> courses){

    }
}
