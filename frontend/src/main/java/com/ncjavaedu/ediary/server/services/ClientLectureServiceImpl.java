package com.ncjavaedu.ediary.server.services;

import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.client.services.ClientLectureService;
import com.ncjavaedu.ediary.services.LectureService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ClientLectureServiceImpl extends BaseServiceImpl implements ClientLectureService {

    @Autowired
    private LectureService lectureService;

    @Override
    public List<LectureDTO> getLectures() {
        List<Lecture> remoteLectures = lectureService.getLectures();
        List<LectureDTO> lectures = new ArrayList<>();
        for (Lecture lecture : remoteLectures){
            LectureDTO dto = ServiceUtils.lectureToDto(lecture);
            if(lecture.getCourse() != null) {
                dto.setCourse(ServiceUtils.courseToDto(lecture.getCourse()));
                if(lecture.getCourse().getLecturer() != null){
                    ServiceUtils.linkCourseToLecturerDto(dto.getCourse(),lecture.getCourse());
                }
            }
            lectures.add(dto);
        }
        return lectures;
    }

    @Override
    public LectureDTO saveLecture(LectureDTO dto) {
        Lecture lecture = ServiceUtils.lectureDtoToLecture(dto);
//        lecture.setCourse(ServiceUtils.courseDtoToCourse(dto.getCourse()));
        lectureService.saveLecture(lecture);

        //Update userId after save
        dto.setLectureId(lecture.getLectureId());
        return dto;
    }

    @Override
    public LectureDTO deleteLecture(LectureDTO dto){
        Lecture lecture = ServiceUtils.lectureDtoToLecture(dto);
        lectureService.deleteLecture(lecture);
        return dto;
    }
}
