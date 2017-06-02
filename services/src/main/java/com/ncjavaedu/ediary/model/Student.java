package com.ncjavaedu.ediary.model;

public class Student extends User {
    public Student(String firstName, String lastName, String university, String email) {
        super(firstName, lastName, university, email, Role.Student);
    }

    public Student(){}
}
