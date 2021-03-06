package com.ncjavaedu.ediary.services;

import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.model.User;

import java.util.List;

/**
 * Created by abogdanov on 01.04.17.
 */
public interface LectureService {
    List<Lecture> getLectures();

    void saveLecture(Lecture lecture, List<User> students);
    void saveLecture(Lecture lecture);
    void deleteLecture(Lecture lecture);
}
