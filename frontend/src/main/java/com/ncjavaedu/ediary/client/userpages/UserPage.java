package com.ncjavaedu.ediary.client.userpages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.client.schedule.Schedule;
import com.ncjavaedu.ediary.client.services.ClientLectureService;
import com.sencha.gxt.widget.core.client.info.Info;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserPage implements IsWidget {
    @UiField(provided = true)
    Schedule schedule;

    private Widget widget;
    private static UserPageUiBinder uiBinder = GWT.create(UserPageUiBinder.class);

    private static final Logger logger = Logger.getLogger(UserPage.class.getName());

    private UserDTO currentUser;
    private List<LectureDTO> lectures;


    @UiTemplate("UserPage.ui.xml")
    interface UserPageUiBinder extends UiBinder<Widget, UserPage> {
    }

    public UserPage(UserDTO currentUser) {
        this.currentUser = currentUser;
        lectures = new ArrayList<>(currentUser.getAllLectures());
    }

    @Override
    public Widget asWidget() {
        if (widget == null) {
            if (lectures == null) {
                logger.log(Level.INFO, "lectures is null ");
                getAllLectures();
            } else {
                schedule = new Schedule(lectures);
                logger.log(Level.INFO, "lectures is ok " + lectures.size());

            }
            widget = uiBinder.createAndBindUi(this);
        }
        return widget;
    }

    //----------*****Get data*****-----------//

    private void getAllLectures() {
        AsyncCallback<List<LectureDTO>> callback = new AsyncCallback<List<LectureDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Info.display("Ошибка", "Не удалось получить список лекций");
            }

            @Override
            public void onSuccess(List<LectureDTO> lectures) {
                onGetLectures(lectures);
                schedule = new Schedule(lectures);
            }
        };
        ClientLectureService.App.getInstance().getLectures(callback);
    }

    private void onGetLectures(List<LectureDTO> lectures) {
        this.lectures.addAll(lectures);
    }

}
