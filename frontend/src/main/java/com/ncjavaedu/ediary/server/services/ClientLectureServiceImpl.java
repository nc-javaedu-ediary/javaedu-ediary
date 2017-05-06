package com.ncjavaedu.ediary.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.client.services.ClientLectureService;
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
        List<Lecture> remoteLectures = lectureService.getLectures();
        List<LectureDTO> lectures = new ArrayList<>();
        for (Lecture lecture : remoteLectures){
            LectureDTO dto = ServiceUtils.lectureToDto(lecture);
            lectures.add(dto);
        }
        return lectures;
    }

    @Override
    public LectureDTO saveLecture(LectureDTO dto) {
        Lecture lecture = ServiceUtils.lectureDtoToLecture(dto);
        lectureService.saveLecture(lecture);

        //Update userId after save
        dto.setLectureId(lecture.getLectureId());
        return dto;
    }
}