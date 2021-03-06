package com.ncjavaedu.ediary.client.model;

import com.google.gwt.i18n.client.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LectureDTO implements Serializable {
    private Integer lectureId;
    private String title;
    private Date date;
    private String classroom;
    private String description;
    private String homework;
    private CourseDTO course;
    private List<UserDTO> studentsAttendance  = new ArrayList<>();;

    public LectureDTO() {

    }

    public LectureDTO(String title, String classroom, String description, String homework) {
        this.title = title;
        this.classroom = classroom;
        this.description = description;
        this.homework = homework;
    }

    public Integer getLectureId() {
        return lectureId;
    }

    public void setLectureId(Integer lectureId) {
        this.lectureId = lectureId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public CourseDTO getCourse() { return course;}

    public void setCourse(CourseDTO course) { this.course = course; }

    public void setLectureDTO(LectureDTO lectureDTO){
        this.lectureId = lectureDTO.getLectureId();
        this.title = lectureDTO.getTitle();
        this.classroom = lectureDTO.getClassroom();
        this.description = lectureDTO.getDescription();
        this.homework = lectureDTO.getHomework();
        this.course = lectureDTO.getCourse();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDay(){
        return DateTimeFormat.getFormat("dd").format(date);
    }

    public String getLectureDay(){
        return DateTimeFormat.getFormat("dd-MM-yyyy").format(date);
    }

    public String getLectureTime() {
        return DateTimeFormat.getFormat("HH:mm").format(date);
    }

    public List<UserDTO> getStudentsAttendance() {
        return studentsAttendance;
    }

    public void setStudentsAttendance(List<UserDTO> studentsAttendance) {
        this.studentsAttendance = studentsAttendance;
    }
}
