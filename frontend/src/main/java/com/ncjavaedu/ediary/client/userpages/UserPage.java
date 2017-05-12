package com.ncjavaedu.ediary.client.userpages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.LectureDTO;
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

    private static final Logger logger = Logger.getLogger(UserPage.class.getName());

    private List<LectureDTO> lectures;
    private int role;


    @UiTemplate("UserPage.ui.xml")
    interface UserPageUiBinder extends UiBinder<Widget, UserPage> {
    }

    public UserPage(UserDTO currentUser) {
//        if (currentUser.getRole() == RoleDTO.Student)
//            role = 2;
//        else role = 1;
        lectures = new ArrayList<>();
        for (CourseDTO course : currentUser.getCourses()) {
            lectures.addAll(course.getLectures());
            logger.log(Level.INFO, "course " + course.getLecturer().getFirstName());
        }
    }

    @Override
    public Widget asWidget() {
        if (widget == null) {
            timeTable = new ContentPanel();
            if (lectures == null) {
                logger.log(Level.INFO, "lectures is null ");
                timeTable.add(new Schedule());
            } else {
                timeTable.add(new Schedule(lectures));
            }
            widget = uiBinder.createAndBindUi(this);
        }
        return widget;
    }
}
