package com.ncjavaedu.ediary.services;

import com.ncjavaedu.ediary.model.Course;

import java.util.List;

/**
 * Created by abogdanov on 01.04.17.
 */
public interface CourseService {
    List<Course> getCourses();

    void saveCourse(Course course);
}
