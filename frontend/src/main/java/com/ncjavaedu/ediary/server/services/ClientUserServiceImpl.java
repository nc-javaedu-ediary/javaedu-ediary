package com.ncjavaedu.ediary.server.services;

import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.client.services.ClientUserService;
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ClientUserServiceImpl extends BaseServiceImpl implements ClientUserService {

    private static final Logger logger = Logger.getLogger(ClientCourseServiceImpl.class.getName());

    @Autowired
    private UserService userService;

    @Override
    public List<UserDTO> getUsers() {
        return rcvUserDTOList(userService.getUsers());
    }

    @Override
    public UserDTO saveUser(UserDTO dto, List<CourseDTO> courseDTOs){
        User user = ServiceUtils.userDtoToUser(dto);
        List<Course> courses = new ArrayList<>();
        for(CourseDTO c: courseDTOs){
            courses.add(ServiceUtils.courseDtoToCourse(c));
        }
        userService.saveUser(user, courses);

        dto.setCourses(courseDTOs);
        dto.setUserId(user.getUserId());
        return dto;
    }

    @Override
    public UserDTO getUser(String login, String password) {
        User user = userService.getUser(login, password);
        return (user == null) ? null : getUserDTO(user);
    }

    @Override
    public List<UserDTO> getLecturers(){
        return rcvUserDTOList((userService.getLecturers()));
    }

    private List<UserDTO> rcvUserDTOList(List<User> src){
        List<UserDTO> users = new ArrayList<>();
        for (User user : src)
            users.add(getUserDTO(user));
        return users;
    }

    private UserDTO getUserDTO(User user) {
        UserDTO dto = ServiceUtils.userToDto(user);
        if (user.getCourses() != null){
            ServiceUtils.linkUserToCoursesDto(dto, user);
        }
        return dto;
    }

    @Override
    public UserDTO deleteUser(UserDTO dto){
        User user = ServiceUtils.userDtoToUser(dto);
        userService.deleteUser(user);
        return dto;
    }
}
