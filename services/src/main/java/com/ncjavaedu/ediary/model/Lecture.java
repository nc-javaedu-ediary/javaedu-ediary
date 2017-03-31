package com.ncjavaedu.ediary.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Lecture {
    private String title;
    private GregorianCalendar date = new GregorianCalendar();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private String classroom;
    private String description;
    private String homework;

    public Lecture(String title, String date, String classroom, String description) {
        this.title = title;
        try {
            this.date.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.classroom = classroom;
        this.description = description;
    }

    public Lecture(String title, String date, String classroom, String description, String homework) {
        this.title = title;
        try {
            this.date.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.classroom = classroom;
        this.description = description;
        this.homework = homework;
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
}
