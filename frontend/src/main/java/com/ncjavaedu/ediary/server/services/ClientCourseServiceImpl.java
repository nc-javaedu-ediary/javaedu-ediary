package com.ncjavaedu.ediary.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.client.services.ClientCourseService;
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
            courses.add(dto);
        }
        return courses;
    }

    @Override
    public CourseDTO saveCourse(CourseDTO dto) {
        Course course = ServiceUtils.courseDtoToCourse(dto);
        courseService.saveCourse(course);

        //Update userId after save
        dto.setCourseId(course.getCourseId());
        return dto;
    }
}
