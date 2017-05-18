package com.ncjavaedu.ediary.client.userpages.lecture;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LecturePage extends PopupPanel {
    @UiField
    Label theme;
    @UiField
    Label lectDate;
    @UiField
    Label lecturer;
    @UiField
    Label description;
    @UiField
    Label homework;

    @UiField
    VerticalLayoutContainer gridContainer;
    @UiField
    Grid<UserDTO> usersGrid;
    @UiField
    GridView<UserDTO> usersView;
    @UiField(provided = true)
    ColumnModel<UserDTO> usersCM;
    @UiField(provided = true)
    ListStore<UserDTO> usersStore;
    @UiField
    VerticalLayoutContainer nousers;

    private static LecturePageUiBinder uiBinder = GWT.create(LecturePageUiBinder.class);
    private static final LecturePageProps userProps = GWT.create(LecturePageProps.class);

    private LectureDTO lecture;
    private UserDTO lector;
    private CourseDTO course;
    private List<UserDTO> users;
    private List<UserDTO> studentsAttendance;

    private CheckBoxSelectionModel<UserDTO> selectionModel;


    private static final Logger logger = Logger.getLogger(LecturePage.class.getName());
    private boolean selected;

    @UiTemplate("LecturePage.ui.xml")
    interface LecturePageUiBinder extends UiBinder<Widget, LecturePage> {
    }

    public LecturePage(LectureDTO lecture, boolean showUserList) {
        super(true);
        this.lecture = lecture;
        this.users = new ArrayList<>();
        this.studentsAttendance = new ArrayList<>();

        if (lecture.getCourse() != null) {
            course = lecture.getCourse();
            if (course.getLecturer() != null)
                lector = course.getLecturer();
            if (course.getUsers() != null)
                for (UserDTO user : course.getUsers()) {
                    if (!Objects.equals(user.getUserId(), lector.getUserId())) {
                        users.add(user);
                    }
                }
        }

        generateUserList();
        add(uiBinder.createAndBindUi(this));
        showLectInformation();
        if (showUserList) {
            gridContainer.setVisible(true);

            if (usersStore.size() != 0) {
                usersGrid.setSelectionModel(selectionModel);
//                usersGrid.getView().setAutoExpandColumn(usersGrid.getColumnModel().getColumn(1));//!
                usersGrid.getView().setAutoFill(true);
                usersGrid.getView().setForceFit(true);
                usersGrid.getView().setStripeRows(true);
                usersGrid.getView().setColumnLines(true);


                if (studentsAttendance != null && studentsAttendance.size() !=0)
                    for (UserDTO userDTO : studentsAttendance) {
                        logger.log(Level.WARNING, "users3 " + studentsAttendance.size());

                        selectionModel.select(userDTO, true);
                        logger.log(Level.WARNING, "studentsAttendance " + studentsAttendance.size());

                    }

                usersGrid.getParent().setHeight(Integer.toString(20 + users.size() * 22));
            } else
                nousers.setVisible(true);
        }

    }

    private void showLectInformation() {
        theme.setText(lecture.getTitle());

        logger.log(Level.INFO, "lecture.getTitle() ");

        lectDate.setText(lecture.getLectureDay());

        if (lecture.getCourse().getLecturer() != null)
            lecturer.setText(lecture.getCourse().getLecturer().getFullName());

        description.setText(lecture.getDescription());
        if (!Objects.equals(lecture.getHomework(), ""))
            homework.setText(lecture.getHomework());
        else
            homework.setText("Не задано");
    }

    private void generateUserList() {
        IdentityValueProvider<UserDTO> provider = new IdentityValueProvider<>();
        selectionModel = new CheckBoxSelectionModel<UserDTO>(provider){
            @Override
            protected void setChecked(boolean checked) {
                super.setChecked(checked);
                studentsAttendance.clear();
                studentsAttendance.addAll(this.getSelectedItems());
                logger.log(Level.WARNING, "checked " + checked);
            }

        };

        ColumnConfig<UserDTO, Integer> idCol = new ColumnConfig<>(userProps.userId(), 100, "ID");
        ColumnConfig<UserDTO, String> fnCol = new ColumnConfig<>(userProps.firstName(), 200, "Имя");
        ColumnConfig<UserDTO, String> lnCol = new ColumnConfig<>(userProps.lastName(), 300, "Фамилия");

        logger.log(Level.WARNING, "lnCol " + lnCol);

        List<ColumnConfig<UserDTO, ?>> userColumns = new ArrayList<>();
        userColumns.add(selectionModel.getColumn());
        userColumns.add(idCol);
        userColumns.add(fnCol);
        userColumns.add(lnCol);

        usersCM = new ColumnModel<>(userColumns);

        usersStore = new ListStore<>(userProps.key());
        if (users != null && users.size() != 0) {
            usersStore.addAll(users);

            studentsAttendance.addAll(users);
            logger.log(Level.WARNING, "users2 " + studentsAttendance.size());


        }
    }
}