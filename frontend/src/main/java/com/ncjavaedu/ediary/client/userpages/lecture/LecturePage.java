package com.ncjavaedu.ediary.client.userpages.lecture;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.admin.popups.AdminPopupCallbacks;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.client.props.RoleValueProvider;
import com.ncjavaedu.ediary.client.props.UserProps;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;

import java.util.ArrayList;
import java.util.List;


public class LecturePage extends PopupPanel {
    @UiField(provided = true)
    FieldLabel theme;
    @UiField(provided = true)
    FieldLabel lectDate;
    @UiField(provided = true)
    FieldLabel lecturer;
    @UiField(provided = true)
    FieldLabel description;
    @UiField(provided = true)
    FieldLabel homework;

    @UiField
    Grid<UserDTO> usersGrid;
    @UiField
    GridView<UserDTO> usersView;
    @UiField(provided = true)
    ColumnModel<UserDTO> usersCM;
    @UiField(provided = true)
    ListStore<UserDTO> usersStore;

    private static final UserProps userProps = GWT.create(UserProps.class);
    private LectureDTO lecture;
    private CourseDTO course;
    private List<UserDTO> users;

    private static LecturePageUiBinder uiBinder = GWT.create(LecturePageUiBinder.class);
    private AdminPopupCallbacks cb;

    @UiTemplate("LecturePage.ui.xml")
    interface LecturePageUiBinder extends UiBinder<Widget, LecturePage> {
    }

    public LecturePage() {
        super(false);
        add(uiBinder.createAndBindUi(this));
    }

    public LecturePage(LectureDTO lecture) {
        super(false);
        add(uiBinder.createAndBindUi(this));
        this.lecture = lecture;
        course = lecture.getCourse();
        users = course.getUsers();
    }

    private void generateUserList(){
        ColumnConfig<UserDTO, Integer> idCol = new ColumnConfig<>(userProps.userId(), 20, "ID");
        ColumnConfig<UserDTO, String> fnCol = new ColumnConfig<>(userProps.firstName(), 80, "Имя");
        ColumnConfig<UserDTO, String> lnCol = new ColumnConfig<>(userProps.lastName(), 100, "Фамилия");
//        ColumnConfig<UserDTO, String> checkBoxCol = new ColumnConfig<>(userProps.university(), 80, "Университет");
        RoleValueProvider rvp = new RoleValueProvider();
        ColumnConfig<UserDTO, String> roleCol = new ColumnConfig<>(rvp, 150, "Роль");

        List<ColumnConfig<UserDTO, ?>> userColumns = new ArrayList<>();
        userColumns.add(idCol);
        userColumns.add(fnCol);
        userColumns.add(lnCol);
//        userColumns.add(checkBoxCol);
        userColumns.add(roleCol);

        usersCM = new ColumnModel<>(userColumns);

        usersStore = new ListStore<>(userProps.key());
        if(users != null)
        usersStore.addAll(users);
    }

    public void ShowLecturePopup(final AdminPopupCallbacks cb){
        this.cb = cb;

        center();
        show();
    }
}