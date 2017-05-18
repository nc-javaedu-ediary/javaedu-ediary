package com.ncjavaedu.ediary.server.services;

import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.client.services.ClientCourseService;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ClientCourseServiceImpl extends BaseServiceImpl implements ClientCourseService {

    @Autowired
    private CourseService courseService;

    @Override
    public List<CourseDTO> getCourses() {
        List<Course> remoteCourses = courseService.getCourses();
        List<CourseDTO> courses = new ArrayList<>();
        for (Course course : remoteCourses){
            CourseDTO dto = ServiceUtils.courseToDto(course);
            ServiceUtils.linkCourseToLecturerDto(dto, course);
            ServiceUtils.linkCourseToUsersDto(dto, course, true);
            courses.add(dto);
        }
        return courses;
    }

    @Override
    public CourseDTO saveCourse(CourseDTO dto, UserDTO lecturer, List<LectureDTO> lecturesDTO) {
        Course course = ServiceUtils.courseDtoToCourse(dto);
        User lect = ServiceUtils.userDtoToUser(lecturer);
        List<Lecture> lectures = new ArrayList<>();
        for(LectureDTO l: lecturesDTO){
            lectures.add(ServiceUtils.lectureDtoToLecture(l));
        }
        courseService.saveCourse(course, lect, lectures);

        //Update userId after save
        dto.setLecturer(lecturer);
        dto.setLectures(lecturesDTO);
        dto.setCourseId(course.getCourseId());
        return dto;
    }

    @Override
    public CourseDTO deleteCourse(CourseDTO dto){
        Course course = ServiceUtils.courseDtoToCourse(dto);
        courseService.deleteCourse(course);
        return dto;
    }
}
