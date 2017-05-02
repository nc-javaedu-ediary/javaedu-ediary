package com.ncjavaedu.ediary.client.admin.popups;

import com.ncjavaedu.ediary.client.model.*;

/**
 * Created by abogdanov on 22.04.17.
 */
public interface AdminPopupCallbacks {
    void userPopupValidated(UserDTO user, boolean newUser);
    void lecturePopupValidated(LectureDTO lecture, boolean newLecture);
    void coursePopupValidated(CourseDTO course, boolean newCourse);
}
