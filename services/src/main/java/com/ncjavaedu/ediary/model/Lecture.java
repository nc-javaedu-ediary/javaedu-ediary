package com.ncjavaedu.ediary.model;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

@Entity
@Table(name = "LECTURES")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DATE")
    private GregorianCalendar date = new GregorianCalendar();
    @Transient
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    @Column(name = "CLASSROOM")
    private String classroom;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "HOMEWORK")
    private String homework;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
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

    private void parseDate(String date) {
        try {
            this.date.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
