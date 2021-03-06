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

public final class ServiceUtils {

    public static final UserDTO userToDto(User user) {
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

    public static final LectureDTO lectureToDto(Lecture lecture) {
        LectureDTO dto = new LectureDTO();
        dto.setLectureId(lecture.getLectureId());
        dto.setTitle(lecture.getTitle());

        dto.setDate(lecture.getDate());

        dto.setClassroom(lecture.getClassroom());
        dto.setDescription(lecture.getDescription());
        dto.setHomework(lecture.getHomework());
        return dto;
    }

    public static final Lecture lectureDtoToLecture(LectureDTO dto) {
        Lecture lecture = new Lecture();
        lecture.setLectureId(dto.getLectureId());
        lecture.setTitle(dto.getTitle());

        lecture.setDate(dto.getDate());

        lecture.setClassroom(dto.getClassroom());
        lecture.setDescription(dto.getDescription());
        lecture.setHomework(dto.getHomework());
        return lecture;
    }

    public static final CourseDTO courseToDto(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setTitle(course.getTitle());

        return dto;
    }

    public static final Course courseDtoToCourse(CourseDTO dto) {
        Course course = new Course();
        course.setCourseId(dto.getCourseId());
        course.setTitle(dto.getTitle());

        return course;
    }

    /*links*/

    public static final void linkUserToCoursesDto(UserDTO dto, User user) {
        List<Course> courses = user.getCourses();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course c : courses) {
            CourseDTO cdto = ServiceUtils.courseToDto(c);
            ServiceUtils.linkCourseToLecturerDto(cdto, c);
            ServiceUtils.linkCourseToLecturesDto(cdto, c);
            courseDTOS.add(cdto);
        }
        dto.setCourses(courseDTOS);
    }

    public static final void linkCourseToUsersDto(CourseDTO dto, Course course, boolean addLink) {
        List<User> users = course.getUsers();
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User u : users) {
            UserDTO udto = ServiceUtils.userToDto(u);
            if (addLink)
                ServiceUtils.linkUserToCoursesDto(udto, u);
            usersDTO.add(udto);
        }

        dto.setUsers(usersDTO);
    }

    public static final void linkCourseToLecturesDto(CourseDTO dto, Course course) {
        List<Lecture> rcvLectures = course.getLectures();
        List<LectureDTO> lectureDTOS = new ArrayList<>();
        for (Lecture l : rcvLectures) {
            LectureDTO ldto = ServiceUtils.lectureToDto(l);
            ServiceUtils.linkLectureToCourseDto(ldto, l);
            ServiceUtils.linkLectureToStudentsAttendanceDto(ldto, l);
            lectureDTOS.add(ldto);
        }
        dto.setLectures(lectureDTOS);
    }

    public static final void linkCourseToLecturerDto(CourseDTO dto, Course course) {
        User rcvUser = course.getLecturer();
        if (rcvUser != null) {
            UserDTO userDTO = userToDto(rcvUser);
            dto.setLecturer(userDTO);
        }
    }

    public static final void linkLectureToCourseDto(LectureDTO dto, Lecture lecture) {
        Course rcvCourse = lecture.getCourse();
        if (rcvCourse != null) {
            CourseDTO courseDTO = courseToDto(rcvCourse);
            ServiceUtils.linkCourseToLecturerDto(courseDTO, rcvCourse);
            ServiceUtils.linkCourseToUsersDto(courseDTO, rcvCourse, false);

            dto.setCourse(courseDTO);
        }
    }

    public static final void linkLectureToStudentsAttendanceDto(LectureDTO dto, Lecture lecture) {
        List<User> rcvstudents = lecture.getStudentsAttendance();
        List<UserDTO> studentsAttendanceDTO = new ArrayList<>();
        for (User u : rcvstudents) {
            UserDTO stdto = ServiceUtils.userToDto(u);
            studentsAttendanceDTO.add(stdto);
        }
        dto.setStudentsAttendance(studentsAttendanceDTO);
    }
}
