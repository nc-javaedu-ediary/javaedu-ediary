package com.ncjavaedu.ediary.services.impl;

import com.ncjavaedu.ediary.dao.CourseDao;
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abogdanov on 01.04.17.
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao dao;

    public List<Course> getCourses()
    {
        return dao.list();
    }

    public void saveCourse(Course course, User lecturer, List<Lecture> lectures)
    {
        dao.save(course, lecturer, lectures);
    }

    public void deleteCourse(Course course){

    }
}
