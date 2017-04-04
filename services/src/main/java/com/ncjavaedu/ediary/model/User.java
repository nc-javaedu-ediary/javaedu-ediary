package com.ncjavaedu.ediary.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "UNIVERSITY")
    private String university;
    @Column(name = "EMAIL")
    private String email;

    @Transient
    private Role role;

    @Transient
//    @OneToMany
//    @JoinColumn(name = "COURSE_ID")
//    @ManyToMany
//    @JoinTable(name = "USER_COURSES", joinColumns = {
//            @JoinColumn(name = "USER_ID")}, inverseJoinColumns = {
//            @JoinColumn(name = "COURSE_ID")})
    private List<Course> courses = new ArrayList<>();

    public User() {
    }

    public User(String firstName, String lastName, String university, String email, Role role)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.university = university;
        this.role = role;
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Course> getCourses(){
        return courses;
    }

    public void addCourse(Course course)
    {
        courses.add(course);
    }

    public void removeCourse(Course course)
    {
        courses.remove(course);
    }
}
