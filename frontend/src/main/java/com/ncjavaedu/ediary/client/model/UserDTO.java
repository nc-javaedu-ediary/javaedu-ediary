package com.ncjavaedu.ediary.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable {
    private Integer userId;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String university;
    private String email;
    private RoleDTO role;
    private List<CourseDTO> courses = new ArrayList<>();

    public UserDTO(){

    }

    public UserDTO(String login, String password, String firstName, String lastName, String university, String email,
                   RoleDTO role, List<CourseDTO> courseDTOS){
        setLogin(login);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setUniversity(university);
        setEmail(email);
        setRole(role);
        setCourses(courseDTOS);
    }

    public void setUserDTO(UserDTO userDTO){
        this.userId = userDTO.getUserId();
        this.login = userDTO.getLogin();
        this.password = userDTO.getPassword();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.university = userDTO.getUniversity();
        this.email = userDTO.getEmail();
        this.role = userDTO.getRole();
        this.courses = userDTO.getCourses();
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

    public String getFullName(){
        return firstName.substring(0,1) + ". " + lastName;
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

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }
}
