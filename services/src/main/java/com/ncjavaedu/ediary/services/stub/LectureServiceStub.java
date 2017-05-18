package com.ncjavaedu.ediary.services.stub;

import com.google.gson.reflect.TypeToken;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.LectureService;
import com.ncjavaedu.ediary.utils.JSONUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abogdanov on 01.04.17.
 */
@Service
public class LectureServiceStub implements LectureService {
    private JSONUtils jsonUtils = JSONUtils.getInstance();

    public List<Lecture> getLectures()
    {
        return jsonUtils.readJsonFromResource("/stub/lectures.json",
                new TypeToken<List<Lecture>>(){}.getType(), LectureServiceStub.class);
    }

    public void saveLecture(Lecture lecture, List<User> students) {

    }

    public void saveLecture(Lecture lecture)
    {

    }

    public void deleteLecture(Lecture lecture){

    }
}
