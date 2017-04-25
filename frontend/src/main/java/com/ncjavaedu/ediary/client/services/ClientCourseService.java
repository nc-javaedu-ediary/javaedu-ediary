package com.ncjavaedu.ediary.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ncjavaedu.ediary.client.model.CourseDTO;

import java.util.List;

@RemoteServiceRelativePath("courseService")
public interface ClientCourseService extends RemoteService {
    List<CourseDTO> getCourses();
    CourseDTO saveCourse(CourseDTO courseDTO);

    public static class App {
        private static ClientCourseServiceAsync instance = GWT.create(ClientCourseService.class);

        public static synchronized ClientCourseServiceAsync getInstance() {
            return instance;
        }
    }
}