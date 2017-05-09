package com.ncjavaedu.ediary.client.admin.popups;

import com.ncjavaedu.ediary.client.model.*;

/**
 * Created by abogdanov on 22.04.17.
 */
public interface AdminPopupCallbacks {
    void userPopupValidated(UserDTO user);
    void lecturePopupValidated(LectureDTO lecture);
    void coursePopupValidated(CourseDTO course);
}
