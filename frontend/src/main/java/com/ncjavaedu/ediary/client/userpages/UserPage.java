package com.ncjavaedu.ediary.client.userpages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.MainApplication;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.model.RoleDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.client.schedule.Schedule;
import com.ncjavaedu.ediary.client.services.ClientSessionManagementService;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserPage implements IsWidget {
    @UiField(provided = true)
    ContentPanel timeTable;
    @UiField
    TextButton logoutButton;

    private Widget widget;
    private static UserPageUiBinder uiBinder = GWT.create(UserPageUiBinder.class);

    private List<LectureDTO> lectures;
    private boolean showUserList = false;

    private static final Logger logger = Logger.getLogger(UserPage.class.getName());

    @UiTemplate("UserPage.ui.xml")
    interface UserPageUiBinder extends UiBinder<Widget, UserPage> {
    }

    public UserPage(UserDTO currentUser) {
        if (currentUser != null && currentUser.getRole() == RoleDTO.Lecturer) {
            showUserList = true;
        }

        if (currentUser.getCourses() != null && currentUser.getCourses().size() != 0) {
            lectures = new ArrayList<>();
            for (CourseDTO course : currentUser.getCourses()) {
                if (course.getLectures() != null && course.getLectures().size() != 0) {
                    lectures.addAll(course.getLectures());
                }
            }
        }
    }

    @UiHandler({"logoutButton"})
    public void logoutButtonClick(SelectEvent selectEvent) {
        AsyncCallback<UserDTO> callback1 = new AsyncCallback<UserDTO>() {
            @Override
            public void onFailure(Throwable throwable) {
                Info.display("!", "Failed to store user in session");
            }

            @Override
            public void onSuccess(UserDTO userDTO) {
                Viewport vp = new Viewport();
                vp.add(new MainApplication().asWidget());
                RootLayoutPanel.get().clear();
                RootLayoutPanel.get().add(vp);
            }
        };
        ClientSessionManagementService.App.getInstance().saveUser(null, callback1);
    }

    @Override
    public Widget asWidget() {
        if (widget == null) {
            timeTable = new ContentPanel();
            timeTable.add(new Schedule(lectures, showUserList));
            widget = uiBinder.createAndBindUi(this);
        }
        return widget;
    }


}
