package com.ncjavaedu.ediary.server.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ncjavaedu.ediary.client.model.LectureDTO;

import java.util.List;

@RemoteServiceRelativePath("lectureService")
public interface ClientLectureService extends RemoteService {
    List<LectureDTO> getLectures();
    LectureDTO saveLecture(LectureDTO lectureDTO);

    public static class App {
        private static ClientLectureServiceAsync instance = GWT.create(ClientLectureService.class);

        public static synchronized ClientLectureServiceAsync getInstance() {
            return instance;
        }
    }
}