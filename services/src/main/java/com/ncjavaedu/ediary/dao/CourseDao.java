package com.ncjavaedu.ediary.dao;

import com.ncjavaedu.ediary.model.Course;

import java.util.List;

/**
 * Created by abogdanov on 01.04.17.
 */
public interface CourseDao {
    List<Course> list();
    void save(Course course);
}
