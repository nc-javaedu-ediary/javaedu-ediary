package com.ncjavaedu.ediary.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "LECTURES")
public class Lecture implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LECTURE_ID")
    private Integer lectureId;
    @Column(name = "TITLE")
    private String title;
//    @Column(name = "DATE")
//    private GregorianCalendar date = new GregorianCalendar();
    @Column(name = "DATE")
    private Date date;
    @Transient
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    @Column(name = "CLASSROOM")
    private String classroom;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "HOMEWORK")
    private String homework;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "COURSE_LECTURES", joinColumns = {
//            @JoinColumn(name = "LECTURE_ID")}, inverseJoinColumns = {
//            @JoinColumn(name = "COURSE_ID")})
//   private  List<Course> courses = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="COURSE_LECTURES", joinColumns = {
            @JoinColumn(name="LECTURE_ID")}, inverseJoinColumns = {
            @JoinColumn(name="COURSE_ID")
    })
    @Fetch(value = FetchMode.SELECT)
    private Course course;

    public Lecture() {}

    public Lecture(String title, String date, String classroom, String description) {
        this.title = title;
        parseDate(date);
        this.classroom = classroom;
        this.description = description;
    }

    public Lecture(String title, String date, String classroom, String description, String homework) {
        this.title = title;
        parseDate(date);
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

    public String getStringDate() {
        return dateFormat.format(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) {
        parseDate(date);
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

    public Course getCourse() { return  course; }

    public void setCourse(Course course) { this.course = course; }

    private void parseDate(String date) {
        try {
            this.date = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
