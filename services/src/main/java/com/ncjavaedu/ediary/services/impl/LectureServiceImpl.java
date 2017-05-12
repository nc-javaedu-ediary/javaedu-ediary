package com.ncjavaedu.ediary.services.impl;

import com.ncjavaedu.ediary.dao.LectureDao;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.services.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abogdanov on 01.04.17.
 */
@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureDao dao;

    public List<Lecture> getLectures() { return dao.list(); }

    public void saveLecture(Lecture lecture) { dao.save(lecture); }

    public void deleteLecture(Lecture lecture){}
}
