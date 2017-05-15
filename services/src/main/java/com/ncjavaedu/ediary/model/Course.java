package com.ncjavaedu.ediary.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="COURSE_LECTURERS", joinColumns = {
            @JoinColumn(name="COURSE_ID")}, inverseJoinColumns = {
            @JoinColumn(name="USER_ID")
    })
    @Fetch(value = FetchMode.SELECT)
    private User lecturer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="COURSE_LECTURES", joinColumns = {
            @JoinColumn(name="COURSE_ID")}, inverseJoinColumns = {
            @JoinColumn(name="LECTURE_ID")
    })
    @Fetch(value = FetchMode.SELECT)
    private List<Lecture> lectures = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_COURSES", joinColumns = {
            @JoinColumn(name = "COURSE_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "USER_ID")})
    @Fetch(value = FetchMode.SELECT)
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

    public User getLecturer() {
        return lecturer;
    }

    public void setLecturer(User lecturer) {
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

    public void setLectures(List<Lecture> lectures) { this.lectures = lectures; }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
