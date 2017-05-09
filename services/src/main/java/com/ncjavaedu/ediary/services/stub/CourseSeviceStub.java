package com.ncjavaedu.ediary.services.stub;

import com.google.gson.reflect.TypeToken;
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.CourseService;
import com.ncjavaedu.ediary.utils.JSONUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abogdanov on 01.04.17.
 */
@Service
public class CourseSeviceStub implements CourseService{
    private JSONUtils jsonUtils = JSONUtils.getInstance();

    public List<Course> getCourses()
    {
        return jsonUtils.readJsonFromResource("/stub/courses.json",
                new TypeToken<List<Course>>(){}.getType(), CourseSeviceStub.class);
    }

    public void saveCourse (Course course, User lecturer, List<Lecture> lectures)
    {

    }
}
