package com.ncjavaedu.ediary.client.model;

import javax.jws.soap.SOAPBinding;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseDTO implements Serializable{
    private Integer courseId;
    private String title;

    private UserDTO lecturer;
    private List<LectureDTO> lectures = new ArrayList<>();
    private List<UserDTO> users = new ArrayList<>();

    public CourseDTO(){

    }

    public CourseDTO(String title){
        this.title = title;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserDTO getLecturer() {
        return lecturer;
    }

    public void setLecturer(UserDTO lecturer) {
        this.lecturer = lecturer;
    }

    public List<LectureDTO> getLectures() {
        return lectures;
    }

    public void setLectures(List<LectureDTO> lectures) {
        this.lectures = lectures;
    }

    public void addLecture(LectureDTO lecture) {
        lectures.add(lecture);
    }

    public void removeLecture(LectureDTO lecture) {
        lectures.remove(lecture);
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
