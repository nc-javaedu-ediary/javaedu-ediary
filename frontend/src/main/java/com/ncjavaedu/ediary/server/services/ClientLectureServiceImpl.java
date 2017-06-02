package com.ncjavaedu.ediary.server.services;

import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.client.services.ClientLectureService;
import com.ncjavaedu.ediary.model.User;
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
        List<LectureDTO> lecturesDTO = new ArrayList<>();
        for (Lecture lecture : remoteLectures){
            LectureDTO dto = ServiceUtils.lectureToDto(lecture);
            if(lecture.getCourse() != null) {
                dto.setCourse(ServiceUtils.courseToDto(lecture.getCourse()));
                if(lecture.getCourse().getLecturer() != null){
                    ServiceUtils.linkCourseToLecturerDto(dto.getCourse(),lecture.getCourse());
                }
            }
            if (lecture.getStudentsAttendance() != null)
                ServiceUtils.linkLectureToStudentsAttendanceDto(dto, lecture);

            lecturesDTO.add(dto);
        }
        return lecturesDTO;
    }

    @Override
    public LectureDTO saveLecture(LectureDTO dto) {
        Lecture lecture = ServiceUtils.lectureDtoToLecture(dto);
        lectureService.saveLecture(lecture);

        //Update userId after save
        dto.setLectureId(lecture.getLectureId());
        return dto;
    }

    @Override
    public LectureDTO saveLecture(LectureDTO dto, List<UserDTO> studentsDTO) {
        Lecture lecture = ServiceUtils.lectureDtoToLecture(dto);
        List<User> students = new ArrayList<>();
        for(UserDTO u: studentsDTO){
            students.add(ServiceUtils.userDtoToUser(u));
        }
        lectureService.saveLecture(lecture, students);

        dto.setStudentsAttendance(studentsDTO);
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
