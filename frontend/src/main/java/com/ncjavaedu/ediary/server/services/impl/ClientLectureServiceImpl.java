package com.ncjavaedu.ediary.server.services.impl;


import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.server.services.ClientLectureService;
import com.ncjavaedu.ediary.services.LectureService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//import com.ncjavaedu.ediary.client.model.UserDTO;

public class ClientLectureServiceImpl extends BaseServiceImpl implements ClientLectureService {

    @Autowired
    private LectureService lectureService;

    @Override
    public List<LectureDTO> getLectures() {
        List<Lecture> remoteUsers = lectureService.getLectures();
        List<LectureDTO> lectures = new ArrayList<>();
        for (Lecture lecture : remoteUsers){
            LectureDTO dto = lectureToDto(lecture);
            lectures.add(dto);
        }
        return lectures;
    }

    @Override
    public LectureDTO saveLecture(LectureDTO dto) {
        Lecture lecture = lectureDtoToUser(dto);
        lectureService.saveLecture(lecture);

        //Update userId after save
        dto.setLectureId(lecture.getLectureId());
        return dto;
    }

    private LectureDTO lectureToDto(Lecture lecture){
        LectureDTO dto = new LectureDTO();
        dto.setLectureId(lecture.getLectureId());
        dto.setTitle(lecture.getTitle());
        dto.setClassroom(lecture.getClassroom());
        dto.setDescription(lecture.getDescription());
        dto.setHomework(lecture.getHomework());
        return dto;
    }

    private Lecture lectureDtoToUser(LectureDTO dto){
        Lecture lecture = new Lecture();
        lecture.setLectureId(dto.getLectureId());
        lecture.setTitle(dto.getTitle());
        lecture.setClassroom(dto.getClassroom());
        lecture.setDescription(dto.getDescription());
        lecture.setHomework(dto.getHomework());
        return lecture;
    }
}
