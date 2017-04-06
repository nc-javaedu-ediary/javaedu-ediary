package com.ncjavaedu.ediary.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COURSES")
public class Course implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID")
    private Integer courseId;
    @Column(name = "TITLE")
    private String title;
    @Transient
    private Lecturer lecturer;
    @Transient
    private List<Lecture> lectures = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_COURSES", joinColumns = {
            @JoinColumn(name = "COURSE_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "USER_ID")})
    private List<User> users = new ArrayList<>();

    public Course() {}

    public Course(String title) {
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

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void addLecture(Lecture lecture) {
        lectures.add(lecture);
    }

    public void removeLecture(Lecture lecture) {
        lectures.remove(lecture);
    }
}
