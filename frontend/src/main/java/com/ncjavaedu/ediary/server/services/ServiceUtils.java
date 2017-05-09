package com.ncjavaedu.ediary.server.services;

import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.model.RoleDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.model.Role;
import com.ncjavaedu.ediary.model.User;

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

        return dto;
    }

    public static User userDtoToUser(UserDTO dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUniversity(dto.getUniversity());
        if (dto.getRole() != null) {
            user.setRole(Role.values()[dto.getRole().ordinal()]);
        }
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());

        return user;
    }

    public static final LectureDTO lectureToDto(Lecture lecture){
        LectureDTO dto = new LectureDTO();
        dto.setLectureId(lecture.getLectureId());
        dto.setTitle(lecture.getTitle());

        dto.setDate(lecture.getDate());
        dto.setCourse(courseToDto(lecture.getCourse()));

        dto.setClassroom(lecture.getClassroom());
        dto.setDescription(lecture.getDescription());
        dto.setHomework(lecture.getHomework());
        return dto;
    }

    public static final Lecture lectureDtoToLecture(LectureDTO dto){
        Lecture lecture = new Lecture();
        lecture.setLectureId(dto.getLectureId());
        lecture.setTitle(dto.getTitle());

        lecture.setDate(dto.getDate());
//        course

        lecture.setClassroom(dto.getClassroom());
        lecture.setDescription(dto.getDescription());
        lecture.setHomework(dto.getHomework());
        return lecture;
    }

    public static final CourseDTO courseToDto( Course course){
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setTitle(course.getTitle());

        return dto;
    }

    public static final Course courseDtoToCourse(CourseDTO dto){
        Course course = new Course();
        course.setCourseId(dto.getCourseId());
        course.setTitle(dto.getTitle());

        return course;
    }

    public static final void linkUserToCoursesDto(UserDTO dto, User user){
        List<Course> courses = user.getCourses();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for(Course c: courses){
            courseDTOS.add(ServiceUtils.courseToDto(c));
        }
        dto.setCourses(courseDTOS);
    }

    public static final void linkCourseToLecturesDto(CourseDTO dto, Course course){
        List<Lecture> rcvLectures = course.getLectures();
        List<LectureDTO> lectureDTOS = new ArrayList<>();
        for (Lecture l : rcvLectures) {
            lectureDTOS.add(lectureToDto(l));
        }
        dto.setLectures(lectureDTOS);
    }
}
