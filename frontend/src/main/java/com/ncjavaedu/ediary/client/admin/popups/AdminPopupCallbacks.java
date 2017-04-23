package com.ncjavaedu.ediary.client.admin.popups;

import com.ncjavaedu.ediary.client.model.Course;
import com.ncjavaedu.ediary.client.model.Lecture;
import com.ncjavaedu.ediary.client.model.User;

/**
 * Created by abogdanov on 22.04.17.
 */
public interface AdminPopupCallbacks {
    void userPopupValidated(User user, boolean newUser);
    void lecturePopupValidated(Lecture lecture, boolean newLecture);
    void coursePopupValidated(Course course, boolean newCourse);
}
