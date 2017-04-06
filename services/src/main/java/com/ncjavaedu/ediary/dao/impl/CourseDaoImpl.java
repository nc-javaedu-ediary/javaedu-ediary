package com.ncjavaedu.ediary.dao.impl;

import com.ncjavaedu.ediary.dao.CourseDao;
import com.ncjavaedu.ediary.model.Course;
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
    public void save(Course course){
        getSession().saveOrUpdate(course);
    }
}
