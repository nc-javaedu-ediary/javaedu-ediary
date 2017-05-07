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

final class ServiceUtils {

    static UserDTO userToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUniversity(user.getUniversity());
        if (user.getRole() != null) {
            dto.setRole(RoleDTO.values()[user.getRole().ordinal()]);
        }
        dto.setLogin(user.getLogin());
        dto.setPassword(user.getPassword());

        List<Course> rcvCourses = user.getCourses();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        try {
            for (Course c : rcvCourses) {
                courseDTOS.add(courseToDto(c));
            }
        } catch (LazyInitializationException e) {
            Info.display("Error", "Failed to retrieve courses list from DB");
        }
        dto.setCourses(courseDTOS);
        return dto;
    }

    static User userDtoToUser(UserDTO dto) {
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

        List<Course> courses = new ArrayList<>();
        for (CourseDTO c : dto.getCourses()) {
            courses.add(courseDtoToCourse(c));
        }
        user.setCourses(courses);

        return user;
    }

    static LectureDTO lectureToDto(Lecture lecture) {
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

    static Lecture lectureDtoToLecture(LectureDTO dto) {
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

    static CourseDTO courseToDto(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setTitle(course.getTitle());

//        dto.setLecturer(userToDto(course.getLecturer()));
//        List<LectureDTO> lectures = new ArrayList<>();
//        for(Lecture l : course.getLectures()){
//            lectures.add(lectureToDto(l));
//        }
//        dto.setLectures(lectures);
//        List<UserDTO>

        return dto;
    }

    static Course courseDtoToCourse(CourseDTO dto) {
        Course course = new Course();
        course.setCourseId(dto.getCourseId());
        course.setTitle(dto.getTitle());

//        course.setLecturer((Lecturer)userDtoToUser(dto.getLecturer()));
//        List<Lecture> lectures = new ArrayList<>();
//        for(LectureDTO l : dto.getLectures()){
//            lectures.add(lectureDtoToLecture(l));
//        }
//        course.setLectures(lectures);
//        List<User>

        return course;
    }
}
