package com.ncjavaedu.ediary.dao.impl;

import com.ncjavaedu.ediary.dao.LectureDao;
import com.ncjavaedu.ediary.model.Lecture;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by abogdanov on 01.04.17.
 */
@Repository
public class LectureDaoImpl extends BaseDao<Integer, Lecture> implements LectureDao{
    @Transactional
    public List<Lecture> list()
    {
        Criteria criteria = createEntityCriteria();
        return (List<Lecture>)criteria.list();
    }

    @Transactional
    public void save(Lecture lecture)
    {
        getSession().saveOrUpdate(lecture);
    }
}
