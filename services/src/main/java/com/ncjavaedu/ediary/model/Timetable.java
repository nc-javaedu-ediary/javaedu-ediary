package com.ncjavaedu.ediary.model;

import java.util.List;

public class Timetable {
    private List<Course> usersCourses;
    private List<Lecture> usersLectures;
    private User user;

    public Timetable(User user) {
        this.user = user;
        this.usersCourses = user.getCourses();
        for (Course course: usersCourses) {
            usersLectures.addAll(course.getLectures());
        }
    }
}
