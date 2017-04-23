package com.ncjavaedu.ediary.server.services.impl;

import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.server.services.ClientCourseService;
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
            CourseDTO dto = courseToDto(course);
            courses.add(dto);
        }
        return courses;
    }

    @Override
    public CourseDTO saveCourse(CourseDTO dto) {
        Course course = courseDtoToUser(dto);
        courseService.saveCourse(course);

        //Update userId after save
        dto.setCourseId(course.getCourseId());
        return dto;
    }

    private CourseDTO courseToDto( Course course){
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setTitle(course.getTitle());
//        dto.setLectures(course.getLectures());
        return dto;
    }

    private Course courseDtoToUser(CourseDTO dto){
        Course course = new Course();
        course.setCourseId(dto.getCourseId());
        course.setTitle(dto.getTitle());
        return course;
    }
}
