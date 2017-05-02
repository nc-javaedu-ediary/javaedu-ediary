package com.ncjavaedu.ediary.client.model;

import java.io.Serializable;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.GregorianCalendar;


public class LectureDTO implements Serializable{
    private Integer lectureId;
    private String title;
//    @Column(name = "DATE")
//    private GregorianCalendar date = new GregorianCalendar();
//    @Transient
//    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private String classroom;
    private String description;
    private String homework;
    private CourseDTO course;

    public LectureDTO(){

    }

    public LectureDTO(String title, String classroom, String description, String homework){
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

//    public GregorianCalendar getDate() {
//        return date;
//    }

//    public void setDate(GregorianCalendar date) {
//        this.date = date;
//    }

//    public void setDate(String date) {
//        parseDate(date);
//    }

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

//    private void parseDate(String date) {
//        try {
//            this.date.setTime(dateFormat.parse(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}
