package com.ncjavaedu.ediary.services;

import com.ncjavaedu.ediary.model.User;
import org.springframework.stereotype.Service;

/**
 * Created by abogdanov on 11.05.17.
 */

public interface SessionManagementService {
    User getUser();
    User setUser(User user);
}
