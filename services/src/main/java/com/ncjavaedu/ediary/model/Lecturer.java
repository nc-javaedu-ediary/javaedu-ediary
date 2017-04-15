package com.ncjavaedu.ediary.model;

public class Lecturer extends User {
    public Lecturer(String firstName, String lastName, String university, String email) {
        super(firstName, lastName, university, email, Role.Lecturer);
    }

    public Lecturer() {
    }
}
