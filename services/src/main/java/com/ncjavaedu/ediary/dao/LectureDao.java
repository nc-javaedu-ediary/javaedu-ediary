package com.ncjavaedu.ediary.dao;

import com.ncjavaedu.ediary.model.Lecture;

import java.util.List;

/**
 * Created by abogdanov on 01.04.17.
 */
public interface LectureDao {
    List<Lecture> list();
    void save(Lecture lecture);
}
