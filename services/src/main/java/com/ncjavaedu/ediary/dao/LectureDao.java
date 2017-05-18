package com.ncjavaedu.ediary.dao;

import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.model.User;

import java.util.List;

/**
 * Created by abogdanov on 01.04.17.
 */
public interface LectureDao {
    List<Lecture> list();
    void save(Lecture lecture, List<User> students);
    void save(Lecture lecture);
    void delete(Lecture lecture);
}
