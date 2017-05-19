package com.ncjavaedu.ediary.dao.impl;

import com.ncjavaedu.ediary.dao.CourseDao;
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.model.User;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by abogdanov on 01.04.17.
 */
@Repository
public class CourseDaoImpl extends BaseDao<Integer, Course> implements CourseDao{
    @Transactional
    public List<Course> list() {
        Criteria criteria = createEntityCriteria();
        return (List<Course>)criteria.list();
    }

    @Transactional
    public void save(Course course, User lecturer, List<Lecture> lectures)
    {
        course.setLecturer(lecturer);
        course.setLectures(lectures);
        getSession().saveOrUpdate(course);
    }

    @Transactional
    public void delete(Course course){
        super.delete(course);
    }
}
