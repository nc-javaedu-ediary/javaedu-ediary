package com.ncjavaedu.ediary.server.services;

import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.model.RoleDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.model.Role;
import com.ncjavaedu.ediary.model.User;
import com.sencha.gxt.widget.core.client.info.Info;
import org.hibernate.LazyInitializationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abogdanov on 01.05.17.
 */
public final class ServiceUtils {

    public static final UserDTO userToDto(User user){
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUniversity(user.getUniversity());
        if(user.getRole() != null) {
            dto.setRole(RoleDTO.values()[user.getRole().ordinal()]);
        }
        dto.setLogin(user.getLogin());
        dto.setPassword(user.getPassword());

        List<Course> rcvCourses = user.getCourses();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        try{
            for (Course c : rcvCourses) {
                courseDTOS.add(courseToDto(c));
            }
        }
        catch(LazyInitializationException e){
            Info.display("Error", "Failed to retrieve courses list from DB");
        }
        dto.setCourses(courseDTOS);

        return dto;
    }

    public static final User userDtoToUser(UserDTO dto){
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUniversity(dto.getUniversity());
        if(dto.getRole() != null){
            user.setRole(Role.values()[dto.getRole().ordinal()]);
        }
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());

        List<Course> courses = new ArrayList<>();
        for(CourseDTO c : dto.getCourses()){
            courses.add(courseDtoToCourse(c));
        }
        user.setCourses(courses);

        return user;
    }

    public static final LectureDTO lectureToDto(Lecture lecture){
        LectureDTO dto = new LectureDTO();
        dto.setLectureId(lecture.getLectureId());
        dto.setTitle(lecture.getTitle());
        dto.setClassroom(lecture.getClassroom());
        dto.setDescription(lecture.getDescription());
        dto.setHomework(lecture.getHomework());
        return dto;
    }

    public static final Lecture lectureDtoToLecture(LectureDTO dto){
        Lecture lecture = new Lecture();
        lecture.setLectureId(dto.getLectureId());
        lecture.setTitle(dto.getTitle());
        lecture.setClassroom(dto.getClassroom());
        lecture.setDescription(dto.getDescription());
        lecture.setHomework(dto.getHomework());
        return lecture;
    }

    public static final CourseDTO courseToDto( Course course){
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setTitle(course.getTitle());
//        dto.setLectures(course.getLectures());
        return dto;
    }

    public static final Course courseDtoToCourse(CourseDTO dto){
        Course course = new Course();
        course.setCourseId(dto.getCourseId());
        course.setTitle(dto.getTitle());
        return course;
    }
}
