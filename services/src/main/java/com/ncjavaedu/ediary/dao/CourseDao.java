package com.ncjavaedu.ediary.dao;

import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.model.User;

import java.util.List;

/**
 * Created by abogdanov on 01.04.17.
 */
public interface CourseDao {
    List<Course> list();
    void save(Course course, User lecturer, List<Lecture> lectures);
    void delete(Course course);
}
