package com.ncjavaedu.ediary.client.userpages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.model.RoleDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.client.schedule.Schedule;
import com.sencha.gxt.widget.core.client.ContentPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserPage implements IsWidget {
    @UiField(provided = true)
    ContentPanel timeTable;

    private Widget widget;
    private static UserPageUiBinder uiBinder = GWT.create(UserPageUiBinder.class);

    private List<LectureDTO> lectures;
    private boolean showUserList = false;

    private static final Logger logger = Logger.getLogger(UserPage.class.getName());

    @UiTemplate("UserPage.ui.xml")
    interface UserPageUiBinder extends UiBinder<Widget, UserPage> {
    }

    public UserPage(UserDTO currentUser) {
        logger.log(Level.INFO, "currentUser.getRole() " + currentUser.getRole());

        if (currentUser != null && currentUser.getRole() == RoleDTO.Lecturer) {
            showUserList = true;
            logger.log(Level.INFO, "currentUser.getRole() " + currentUser.getRole());
        }
        logger.log(Level.INFO, "showUserList " + showUserList);
        logger.log(Level.INFO, "currentUser.getCourses() " + currentUser.getCourses().size());

        if (currentUser.getCourses() != null && currentUser.getCourses().size() != 0) {
            logger.log(Level.INFO, "currentUser.getCourses() " + currentUser.getCourses().size());

            lectures = new ArrayList<>();
            for (CourseDTO course : currentUser.getCourses()) {
                if (course.getLectures() != null && course.getLectures().size() != 0) {
                    lectures.addAll(course.getLectures());
                    logger.log(Level.INFO, "course.getLectures() " + course.getLectures().size());
                    logger.log(Level.INFO, "course " + course.getLecturer().getFirstName());
                }
            }
        }
    }

    @Override
    public Widget asWidget() {
        if (widget == null) {
            timeTable = new ContentPanel();
            if (lectures == null || lectures.size() == 0) {
                logger.log(Level.INFO, "lectures is null ");
                timeTable.add(new Schedule());
            } else {
                timeTable.add(new Schedule(lectures, showUserList));
            }
            widget = uiBinder.createAndBindUi(this);
        }
        return widget;
    }
}
